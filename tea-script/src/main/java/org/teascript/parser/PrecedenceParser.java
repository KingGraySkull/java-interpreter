package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Literal;
import org.teascript.ast.expression.Variable;
import org.teascript.ast.statement.*;
import org.teascript.constant.ObjectType;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.SymbolTable;
import org.teascript.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class PrecedenceParser
{
    private final List<Token> tokens;

    private int currentTokenPos;

    private final List<String> errors;

    public Token currentToken;

    public Token previousToken;

    public PrecedenceParser(List<Token> tokens)
    {
        this.currentTokenPos = 0;
        this.tokens = tokens;
        errors = new ArrayList<>();
    }

    public List<Statement> parseProgram()
    {
        advance();
        List<Statement> statements = new ArrayList<>();
        while (this.currentToken.getTokenType() != TokenType.EOF)
        {
            try
            {
                Statement currentStatement = parseDecalaration();
                statements.add(currentStatement);
            } catch (ParserException e)
            {
                errors.add(e.getMessage());
                recoverAndSync();
            }
        }
        return statements;
    }

    private Statement parseDecalaration() throws ParserException
    {
        if (match(TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING, TokenType.BOOL, TokenType.VAR))
        {
            ObjectType type = SymbolTable.getObjectType(previousToken.getTokenType());
            return parseVariableDecalaration(type);
        }
        else if (match(TokenType.FUN))
        {
            return parseFunctionDeclaration("function");
        }
        else if (match(TokenType.CLASS))
        {
            return parseClassDeclaration();
        }
        return parseStatement();
    }

    private Statement parseClassDeclaration() throws ParserException
    {
        consume(TokenType.IDENTIFIER, "Expect class name.");
        Token className = previousToken;

        Variable superClass = null;
        if (match(TokenType.EXTENDS))
        {
            consume(TokenType.IDENTIFIER, "Expect superclass name.");
            superClass = new Variable(previousToken);
        }

        consume(TokenType.LEFT_BRACE, "Expect '{' before class body.");
        List<Statement> methods = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isEnd())
        {
            methods.add(parseFunctionDeclaration("method"));
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after class body.");
        return new ClassStatement(className, methods, superClass);
    }

    private Statement parseFunctionDeclaration(String kind) throws ParserException
    {
        Statement statement = null;
        if (match(TokenType.INT, TokenType.LONG, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING, TokenType.BOOL, TokenType.VAR, TokenType.VOID))
        {
            TokenType type = previousToken.getTokenType();
            ObjectType returnType = SymbolTable.getObjectType(type);
            consume(TokenType.IDENTIFIER, "Expect " + kind + " name.");
            Token functionName = previousToken;
            consume(TokenType.LEFT_PAREN, "Expect '(' after " + kind + " name");
            List<Token> parameters = new ArrayList<>();
            if (!check(TokenType.RIGHT_PAREN))
            {
                do
                {
                    if (parameters.size() >= 255)
                    {
                        System.out.println("Can't have more than 255 parameters. at line no " + previousToken.getLineNo());
                    }
                    consume(TokenType.IDENTIFIER, "Expect parameter name.");
                    parameters.add(previousToken);
                } while (match(TokenType.COMMA));
            }
            consume(TokenType.RIGHT_PAREN, "Expect ')' after " + kind + " name.");
            consume(TokenType.LEFT_BRACE, "Expect '{' before " + kind + " body.");
            Statement body = parseBlock();
            statement = new Fun(functionName, returnType, parameters, body);
        }
        else
        {
            throw new ParserException("Expect return type after "+previousToken.getLexeme()+" at line no " + previousToken.getLineNo());
        }
        return statement;
    }

    //single variable declaration or array declaration
    private Statement parseVariableDecalaration(ObjectType type) throws ParserException
    {
        consume(TokenType.IDENTIFIER, "Expect variable name after token '"+previousToken.getLexeme()+"' at line no " + previousToken.getLineNo());
        Token variableName = previousToken;
        Expression initializer = null;
        if (match(TokenType.EQUAL))
        {
            if (match(TokenType.LEFT_BRACE))
            {
                List<Expression> expressions = new ArrayList<>();
                if (!check(TokenType.RIGHT_BRACE) && !isEnd())
                {
                    do
                    {
                        Expression expression = parseExpression(Precedence.NONE.getPrecedence() + 1);
                        expressions.add(expression);
                    } while (match(TokenType.COMMA));
                }
                consume(TokenType.RIGHT_BRACE, "Error : Expect closing '}' after expression at line no "+previousToken.getLineNo());
                consume(TokenType.SEMICOLON, "Error : Expect ';' after '"+previousToken.getLexeme()+"' at line no " + previousToken.getLineNo());
                return new ArrayDeclaration(variableName, type, expressions, expressions.size());
            }
            else
            {
                initializer = parseExpressionStatement();
                consume(TokenType.SEMICOLON, "Error : Expect ';' after '"+previousToken.getLexeme()+"' at line no " + previousToken.getLineNo());
            }
        }
        else if (match(TokenType.LEFT_SQUARE_BRACKET))
        {
            consume(TokenType.NUMBER, "Error : Expect size of array after '[' at line no "+previousToken.getLineNo());
            int arraySize = -1;
            try
            {
                arraySize = Integer.parseInt(previousToken.getLexeme().toString());
            }
            catch (NumberFormatException e)
            {
                throw new ParserException("Expect integer after '[' at line no : "+previousToken.getLineNo());
            }
            consume(TokenType.RIGHT_SQUARE_BRACKET, "Error : Expect closing ']' after expression at line no "+previousToken.getLineNo());
            consume(TokenType.SEMICOLON, "Error : Expect ';' after '"+previousToken.getLexeme()+"' at line no " + previousToken.getLineNo());
            ArrayDeclaration arrayDeclaration = new ArrayDeclaration(variableName, type, arraySize);
            return arrayDeclaration;
        }
        else
        {
            consume(TokenType.SEMICOLON, "Error : Expect ';' after '"+previousToken.getLexeme()+"' at line no " + previousToken.getLineNo());
        }
        return new VariableDeclarationStatement(type, variableName, initializer);
    }

    private Statement parseStatement() throws ParserException
    {
        Statement statement;
        if (match(TokenType.IF))
        {
            consume(TokenType.LEFT_PAREN, "Expect '(' after if statement.");
            Expression condition = parseExpression(Precedence.NONE.getPrecedence() + 1);
            consume(TokenType.RIGHT_PAREN, "Expect ')' after condition.");

            Statement thenBranch = parseStatement();
            Statement elseBranch = null;
            if (match(TokenType.ELSE))
            {
                elseBranch = parseStatement();
            }
            return new IfElse(condition, thenBranch, elseBranch);
        }
        else if (match(TokenType.WHILE))
        {
            consume(TokenType.LEFT_PAREN, "Expect '(' after while statement.");
            Expression condition = parseExpression(Precedence.NONE.getPrecedence() + 1);
            consume(TokenType.RIGHT_PAREN, "Expect ')' after condition.");
            Statement body = parseStatement();
            return new While(condition, body);
        }
        else if (match(TokenType.FOR))
        {
            List<Statement> statements = new ArrayList<>();
            consume(TokenType.LEFT_PAREN, "Expect '(' after for statement.");
            Statement initializer;
            Expression condition;
            Expression iterator;

            // parse initializer
            if (match(TokenType.SEMICOLON))
            {
                initializer = null;
            }
            else
            {
                initializer = parseDecalaration();
            }

            // parse truthy condition
            if (match(TokenType.SEMICOLON))
            {
                condition = new Literal(new Token(TokenType.BOOL, true, previousToken.getLineNo()));
            }
            else
            {
                condition = parseExpression(Precedence.NONE.getPrecedence() + 1);
                consume(TokenType.SEMICOLON, "Error : Expect ';' after condition at line no " + previousToken.getLineNo());
            }

            //parse iteration
            if (match(TokenType.SEMICOLON))
            {
                iterator = new Literal(new Token(TokenType.BOOL, true, previousToken.getLineNo()));
            }
            else
            {
                iterator = parseExpression(Precedence.NONE.getPrecedence() + 1);
            }
            consume(TokenType.RIGHT_PAREN, "Expect ')' after condition.");
            Statement body = parseStatement();
            statements.add(new For(initializer, condition, iterator, body));
            return new Block(statements);
        }
        else if (match(TokenType.LOOP))
        {
            consume(TokenType.LEFT_PAREN, "Expect '(' after loop statement.");
            Expression range = parseExpressionStatement();
            consume(TokenType.COMMA, "Expect ',' after range condition");
            Expression iterator = parseExpressionStatement();
            consume(TokenType.RIGHT_PAREN, "Expect ')' after condition.");
            Statement body = parseStatement();
            return new Loop(range, iterator, body);
        }
        else if (match(TokenType.PRINT))
        {
            consume(TokenType.LEFT_PAREN, "Expect '(' after print statement.");
            Expression expression = parseExpression(Precedence.NONE.getPrecedence() + 1);
            consume(TokenType.RIGHT_PAREN, "Expect ')' after print statement.");
            consume(TokenType.SEMICOLON, "Expect ';' after print statement at line no " + previousToken.getLineNo());
            statement = new Print(expression);
        }
        else if (match(TokenType.PRINT_LN))
        {
            consume(TokenType.LEFT_PAREN, "Expect '(' after println statement.");
            Expression expression = parseExpression(Precedence.NONE.getPrecedence() + 1);
            consume(TokenType.RIGHT_PAREN, "Expect ')' after println statement.");
            consume(TokenType.SEMICOLON, "Expect ';' after println statement at line no " + previousToken.getLineNo());
            Print printState = new Print(expression);
            printState.setNewLine(true);
            statement = printState;
        }
        else if (match(TokenType.RETURN))
        {
            Token keyword = previousToken;
            Expression value = null;
            if (!check(TokenType.SEMICOLON))
            {
                value = parseExpression(Precedence.NONE.getPrecedence() + 1);
            }
            consume(TokenType.SEMICOLON, "Expect ';' after return expression");
            return new ReturnStatement(keyword, value);
        }
        else if (match(TokenType.LEFT_BRACE))
        {
            statement = parseBlock();
        }
        else
        {
            Expression expression = parseExpressionStatement();
            consume(TokenType.SEMICOLON, "Error : Expect ';' at line no " + previousToken.getLineNo());
            statement = new ExpressionStatement(expression);
        }
        return statement;
    }

    private Statement parseBlock() throws ParserException
    {
        List<Statement> statements = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isEnd())
        {
            Statement statement = parseDecalaration();
            statements.add(statement);
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' at line no " + previousToken.getLineNo());
        return new Block(statements);
    }

    public Expression parseExpressionStatement()
    {
        try
        {
            return parseExpression(Precedence.NONE.getPrecedence() + 1);
        }
        catch (ParserException e)
        {
            errors.add(e.getMessage());
            recoverAndSync();
        }
        return null;
    }

    public Expression parseExpression(int precedence) throws ParserException
    {
        advance();
        PrefixParser prefixParser = ParserTable.getPrefixParser(previousToken.getTokenType());

        if (prefixParser == null)
        {
            throw new ParserException("Unidentified token '" + previousToken.getLexeme() + "' at line no " + previousToken.getLineNo());
        }
        Expression root = prefixParser.parse(previousToken, this);
        while (precedence < PrecedenceTable.getPrecedence(currentToken.getTokenType()))
        {
            advance();
            InfixParser infixParser = ParserTable.getInfixParser(previousToken.getTokenType());
            if (infixParser == null)
            {
                throw new ParserException("Unidentified token '" + previousToken.getLexeme() + "' at line no " + previousToken.getLineNo());
            }
            root = infixParser.parse(this, root, previousToken);
        }
        return root;
    }

    public void consume(TokenType expected, String message) throws ParserException
    {
        if (match(expected))
        {
            return;
        }
        throw new ParserException(message);
    }

    public Token advance()
    {
        this.previousToken = this.currentToken;
        if (!isEnd())
        {
            this.currentToken = tokens.get(this.currentTokenPos);
            this.currentTokenPos++;
        }
        return null;
    }

    public void recoverAndSync()
    {
        advance();
        while (!isEnd())
        {
            if (previousToken.getTokenType() == TokenType.SEMICOLON)
            {
                return;
            }
            switch (peek().getTokenType())
            {
                case VAR:
                case INT:
                case LONG:
                case DOUBLE:
                case FLOAT:
                case STRING:
                case BOOL:
                case IF:
                case WHILE:
                case FOR:
                case PRINT:
                case PRINT_LN:
                case LOOP:
                case CLASS:
                case RETURN:
                case FUN:
                    return;
            }
            advance();
        }
    }

    private Token peek()
    {
        return tokens.get(this.currentTokenPos);
    }

    public boolean check(TokenType type)
    {
        return this.currentToken.getTokenType() == type;
    }

    public boolean match(TokenType expected)
    {
        if (currentToken.getTokenType() == expected)
        {
            advance();
            return true;
        }
        return false;
    }

    public boolean match(TokenType... expectedTypes)
    {
        for (TokenType type : expectedTypes)
        {
            if (currentToken.getTokenType() == type)
            {
                advance();
                return true;
            }
        }
        return false;
    }

    public boolean hasErrors()
    {
        return !this.errors.isEmpty();
    }

    public void logErrorMessages()
    {
        for (String msg : errors)
        {
            System.out.println(msg);
        }
    }

    public boolean isEnd()
    {
        return this.currentTokenPos >= tokens.size();
    }

}

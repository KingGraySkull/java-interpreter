package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Prefix;
import org.teascript.constant.Precedence;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class PrefixExpressionParser implements PrefixParser
{
    @Override
    public Expression parse(Token token, PrecedenceParser parser) throws ParserException
    {
        int precedence = 0;
        switch (token.getTokenType())
        {
            case MINUS:
            case NOT:
            case INCREMENT:
            case DECREMENT:
                precedence = Precedence.PREFIX.getPrecedence();
                break;
            default:
                throw new ParserException("Unidentified token '"+token.getLexeme()+"' at line no "+token.getLineNo());
        }
        Expression rightOperand = parser.parseExpression(precedence);
        return new Prefix(token, rightOperand);
    }
}

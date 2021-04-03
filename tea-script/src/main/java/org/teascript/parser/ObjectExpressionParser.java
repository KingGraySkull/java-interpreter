package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.GetProperty;
import org.teascript.ast.expression.SetProperty;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class ObjectExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        parser.consume(TokenType.IDENTIFIER, "Expect property name after '.'.");
        Token name = parser.previousToken;

        if (parser.match(TokenType.EQUAL))
        {
            Expression rightExpression = parser.parseExpression(Precedence.ASSIGNMENT.getPrecedence());
            return new SetProperty(name, leftOperand, rightExpression);
        }
        else
        {
            return new GetProperty(name, leftOperand);
        }
    }
}

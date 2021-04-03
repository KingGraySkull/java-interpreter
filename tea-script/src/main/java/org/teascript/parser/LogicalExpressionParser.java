package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Logical;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class LogicalExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        TokenType type = operator.getTokenType();
        int precedence = 0;
        if (type == TokenType.LOGICAL_OR)
        {
            precedence = Precedence.OR.getPrecedence();
        }
        else if (type == TokenType.LOGICAL_AND)
        {
            precedence = Precedence.AND.getPrecedence();
        }
        Expression right = parser.parseExpression(precedence);
        return new Logical(leftOperand, operator, right);
    }
}

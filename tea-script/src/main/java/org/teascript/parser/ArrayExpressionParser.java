package org.teascript.parser;

import org.teascript.ast.expression.ArrayVariable;
import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Variable;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class ArrayExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        if (!(leftOperand instanceof Variable))
        {
            throw new ParserException("Left operand must be variable at line no "+ operator.getLineNo());
        }
        if (parser.match(TokenType.RIGHT_SQUARE_BRACKET))
        {
            Variable leftIdentifier = (Variable) leftOperand;
            return new ArrayVariable(leftIdentifier.getName(), operator, null);
        }
        else
        {
            Expression rightOperand = parser.parseExpression(Precedence.NONE.getPrecedence() + 1);
            parser.consume(TokenType.RIGHT_SQUARE_BRACKET, "Expect ']' after expression at line no "+operator.getLineNo());
            Variable leftIdentifier = (Variable) leftOperand;
            return new ArrayVariable(leftIdentifier.getName(), operator, rightOperand);
        }
    }
}

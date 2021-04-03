package org.teascript.parser;

import org.teascript.ast.expression.CompoundAssign;
import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Variable;
import org.teascript.constant.Precedence;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class CompoundAssignExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        if (!(leftOperand instanceof Variable))
        {
            throw new ParserException("Left operand must be variable at line no "+ operator.getLineNo());
        }
        Variable variable = (Variable) leftOperand; // if leftOperand is not variable throw exception
        int precedence = 0;
        switch (operator.getTokenType())
        {
            case PLUS_ASSIGN:
            case MINUS_ASSIGN:
                precedence = Precedence.SUM.getPrecedence();
                break;
            case STAR_ASSIGN:
            case SLASH_ASSIGN:
            case MODULO_ASSIGN:
                precedence = Precedence.PRODUCT.getPrecedence();
                break;
            default:
                throw new ParserException("Unidentified token '"+operator.getLexeme()+"' at line no "+operator.getLineNo());
        }
        Expression rightOperand = parser.parseExpression(precedence);
        return new CompoundAssign(variable, operator, rightOperand);
    }
}

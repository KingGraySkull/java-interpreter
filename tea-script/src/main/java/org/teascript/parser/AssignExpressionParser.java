package org.teascript.parser;

import org.teascript.ast.expression.ArrayVariable;
import org.teascript.ast.expression.Assign;
import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Variable;
import org.teascript.constant.Precedence;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class AssignExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        Expression root = null;
        if (leftOperand instanceof Variable)
        {
            Variable variable = (Variable) leftOperand;
            Expression assignedValue = parser.parseExpression(Precedence.ASSIGNMENT.getPrecedence());
            root = new Assign(variable,operator, assignedValue);
        }
        else if (leftOperand instanceof ArrayVariable)
        {
            ArrayVariable variable = (ArrayVariable) leftOperand;
            Expression assignedValue = parser.parseExpression(Precedence.ASSIGNMENT.getPrecedence());
            return new Assign(variable,operator, assignedValue);
        }
        else
        {
            throw new ParserException("Left operand must be variable at line no "+ operator.getLineNo());
        }
        return root;
    }
}

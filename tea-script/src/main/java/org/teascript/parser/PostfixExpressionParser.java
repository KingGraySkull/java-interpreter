package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Postfix;
import org.teascript.lexer.Token;

public class PostfixExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator)
    {
        int precedence = PrecedenceTable.getPrecedence(operator.getTokenType());
        return new Postfix(leftOperand, operator);
    }
}

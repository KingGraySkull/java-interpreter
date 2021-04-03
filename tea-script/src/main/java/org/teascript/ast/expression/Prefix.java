package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Prefix implements Expression
{
    private final Expression right;

    private final Token operator;

    public Prefix(Token operator, Expression right)
    {
        this.operator = operator;
        this.right = right;
    }

    public Expression getRight()
    {
        return right;
    }

    public Token getOperator()
    {
        return operator;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitPrefixExpression(this);
    }
}

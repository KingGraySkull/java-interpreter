package org.teascript.ast.expression;

import org.teascript.pattern.ExpressionVisitor;

public class Grouping implements Expression
{
    private final Expression expression;

    public Grouping(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression()
    {
        return expression;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitGroupingExpression(this);
    }
}

package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class ExpressionStatement implements Statement
{
    private final Expression expression;

    public ExpressionStatement(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitExpressionStatement(this);
    }
}

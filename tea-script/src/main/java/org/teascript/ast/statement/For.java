package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class For implements Statement
{
    private final Statement initializer;

    private final Expression condition;

    private final Expression iterator;

    private final Statement body;

    public For(Statement initializer, Expression condition, Expression iterator, Statement body)
    {
        this.initializer = initializer;
        this.condition = condition;
        this.iterator = iterator;
        this.body = body;
    }

    public Statement getInitializer() {
        return initializer;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getIterator() {
        return iterator;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitFor(this);
    }
}

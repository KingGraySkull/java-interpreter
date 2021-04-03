package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class Loop implements Statement
{
    private final Expression range;

    private final Expression iterator;

    private final Statement body;

    public Loop(Expression range, Expression iterator, Statement body)
    {
        this.range = range;
        this.iterator = iterator;
        this.body = body;
    }

    public Expression getRange()
    {
        return range;
    }

    public Expression getIterator()
    {
        return iterator;
    }

    public Statement getBody()
    {
        return body;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitLoop(this);
    }
}

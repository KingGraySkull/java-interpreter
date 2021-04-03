package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class While implements Statement
{
    private final Expression condition;

    private final Statement statement;

    public While(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}

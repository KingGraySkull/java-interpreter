package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class IfElse implements Statement
{
    private final Expression condition;

    private final Statement then;

    private final Statement elseStatement;

    public IfElse(Expression condition, Statement then, Statement elseStatement)
    {
        this.condition = condition;
        this.then = then;
        this.elseStatement = elseStatement;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThen() {
        return then;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitIfElse(this);
    }
}

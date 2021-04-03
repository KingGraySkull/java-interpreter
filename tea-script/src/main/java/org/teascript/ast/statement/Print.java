package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.pattern.StatementVisitor;

public class Print implements Statement
{
    private final Expression expression;

    private boolean newLine;

    public Print(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitPrint(this);
    }
}

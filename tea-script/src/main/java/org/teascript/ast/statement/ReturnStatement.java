package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.lexer.Token;
import org.teascript.pattern.StatementVisitor;

public class ReturnStatement implements Statement
{
    private final Token keyword;

    private final Expression value;

    public ReturnStatement(Token keyword, Expression value)
    {
        this.keyword = keyword;
        this.value = value;
    }

    public Token getKeyword()
    {
        return keyword;
    }

    public Expression getValue()
    {
        return value;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitReturn(this);
    }
}

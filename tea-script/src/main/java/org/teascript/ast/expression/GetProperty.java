package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class GetProperty implements Expression
{
    private final Token name;

    private final Expression object;

    public GetProperty(Token name, Expression object)
    {
        this.name = name;
        this.object = object;
    }

    public Token getName()
    {
        return name;
    }

    public Expression getObject()
    {
        return object;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitGetProperty(this);
    }
}

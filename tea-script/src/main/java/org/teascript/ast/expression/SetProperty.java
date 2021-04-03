package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class SetProperty implements Expression
{
    private final Token name;

    private final Expression object;

    private final Expression rightExpression;

    public SetProperty(Token name, Expression object, Expression rightExpression)
    {
        this.name = name;
        this.object = object;
        this.rightExpression = rightExpression;
    }

    public Token getName()
    {
        return name;
    }

    public Expression getObject()
    {
        return object;
    }

    public Expression getRightExpression()
    {
        return rightExpression;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitSetProperty(this);
    }
}

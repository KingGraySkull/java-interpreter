package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Infix implements Expression
{
    private final Expression left;

    private final Token operator;

    private final Expression right;

    public Infix(Expression left, Token operator, Expression right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft()
    {
        return left;
    }

    public Token getOperator()
    {
        return operator;
    }

    public Expression getRight()
    {
        return right;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitInfixExpression(this);
    }
}

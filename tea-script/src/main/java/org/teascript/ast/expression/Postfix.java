package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Postfix implements Expression
{
    private final Expression left;

    private final Token operator;

    public Postfix(Expression left, Token operator)
    {
        this.left = left;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return null;
    }
}

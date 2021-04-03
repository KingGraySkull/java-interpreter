package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Variable implements Expression
{
    private final Token name;

    public Variable(Token name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }

    public Token getName() {
        return name;
    }
}

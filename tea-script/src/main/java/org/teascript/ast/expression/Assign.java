package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Assign implements Expression
{
    private final Variable lValue;

    private final Token operator;

    private final Expression rValue;

    public Assign(Variable lValue, Token operator, Expression rValue) {
        this.lValue = lValue;
        this.operator = operator;
        this.rValue = rValue;
    }

    public Variable getlValue() {
        return lValue;
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getrValue() {
        return rValue;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitAssign(this);
    }
}

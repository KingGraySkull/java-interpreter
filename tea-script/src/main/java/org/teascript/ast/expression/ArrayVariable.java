package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class ArrayVariable extends Variable
{
    private final Expression arrayIndex;

    private final Token operator;

    public ArrayVariable(Token name, Token operator, Expression arrayIndex)
    {
        super(name);
        this.operator = operator;
        this.arrayIndex = arrayIndex;
    }

    public Token getOperator()
    {
        return operator;
    }

    public Expression getArrayIndex()
    {
        return arrayIndex;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitArrayVariable(this);
    }

    @Override
    public Token getName()
    {
        return super.getName();
    }
}

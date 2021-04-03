package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class ThisExpression implements Expression
{
    private final Token keyword;

    public ThisExpression(Token keyword)
    {
        this.keyword = keyword;
    }

    public Token getKeyword()
    {
        return keyword;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitThis(this);
    }
}

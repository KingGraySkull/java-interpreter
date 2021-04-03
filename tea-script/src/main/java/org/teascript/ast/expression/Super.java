package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Super implements Expression
{
    private final Token keyword;

    private final Token method;

    public Super(Token keyword, Token method)
    {
        this.keyword = keyword;
        this.method = method;
    }

    public Token getKeyword()
    {
        return keyword;
    }

    public Token getMethod()
    {
        return method;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitSuper(this);
    }
}

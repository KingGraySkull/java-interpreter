package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

public class Literal implements Expression
{
    private final Token value;

    public Literal(Token lexeme)
    {
        this.value = lexeme;
    }

    public Token getValue()
    {
        return value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitLiteral(this);
    }
}

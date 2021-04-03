package org.teascript.ast.expression;

import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;

import java.util.List;

public class Call implements Expression
{
    private final Expression callee;

    private final Token paren;

    private final List<Expression> arguments;


    public Call(Expression callee, Token paren, List<Expression> arguments)
    {
        this.callee = callee;
        this.paren = paren;
        this.arguments = arguments;
    }

    public Expression getCallee()
    {
        return callee;
    }

    public Token getParen()
    {
        return paren;
    }

    public List<Expression> getArguments()
    {
        return arguments;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor)
    {
        return visitor.visitCall(this);
    }
}

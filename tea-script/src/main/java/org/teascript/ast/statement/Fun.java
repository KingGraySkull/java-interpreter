package org.teascript.ast.statement;

import org.teascript.constant.ObjectType;
import org.teascript.lexer.Token;
import org.teascript.pattern.StatementVisitor;

import java.util.List;

public class Fun implements Statement
{
    private final Token name;

    private final ObjectType returnType;

    private final List<Token> parameters;

    private final Statement body;

    public Fun(Token name, ObjectType returnType, List<Token> parameters, Statement body)
    {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    public Token getName()
    {
        return name;
    }

    public ObjectType getReturnType()
    {
        return returnType;
    }

    public List<Token> getParameters()
    {
        return parameters;
    }

    public Statement getBody()
    {
        return body;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitFun(this);
    }
}

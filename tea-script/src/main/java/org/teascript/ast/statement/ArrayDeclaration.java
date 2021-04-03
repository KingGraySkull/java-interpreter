package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.constant.ObjectType;
import org.teascript.lexer.Token;
import org.teascript.pattern.StatementVisitor;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeclaration implements Statement
{
    private final Token name;

    private final ObjectType type;

    private final List<Expression> list;

    private final int size;

    public ArrayDeclaration(Token name, ObjectType type, int size)
    {
        this.name = name;
        this.type = type;
        this.size = size;
        list = null;
    }

    public ArrayDeclaration(Token name, ObjectType type, List<Expression> list, int size)
    {
        this.name = name;
        this.type = type;
        this.list = list;
        this.size = size;
    }

    public Token getName()
    {
        return name;
    }

    public ObjectType getType()
    {
        return type;
    }

    public List<Expression> getList()
    {
        return list;
    }

    public int getSize()
    {
        return size;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor)
    {
        return visitor.visitArrayDeclaration(this);
    }
}

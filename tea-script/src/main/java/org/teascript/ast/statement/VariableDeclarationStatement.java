package org.teascript.ast.statement;

import org.teascript.ast.expression.Expression;
import org.teascript.constant.ObjectType;
import org.teascript.lexer.Token;
import org.teascript.pattern.StatementVisitor;

public class VariableDeclarationStatement implements Statement
{
    private final ObjectType dataType;

    private final Token identifierName;

    private final Expression initializer;

    public VariableDeclarationStatement(ObjectType dataType, Token identifierName, Expression initializer)
    {
        this.dataType = dataType;
        this.identifierName = identifierName;
        this.initializer = initializer;
    }

    public ObjectType getDataType() {
        return dataType;
    }

    public Token getIdentifierName() {
        return identifierName;
    }

    public Expression getInitializer() {
        return initializer;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitVariableDeclarationStatement(this);
    }
}

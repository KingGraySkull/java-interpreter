package org.teascript.ast.statement;

import org.teascript.ast.expression.Variable;
import org.teascript.function.Function;
import org.teascript.lexer.Token;
import org.teascript.pattern.StatementVisitor;

import java.util.List;

public class ClassStatement implements Statement
{
    private final Token className;

    private final List<Statement> methods;

    private final Variable superClass;

    public ClassStatement(Token className, List<Statement> methods, Variable superClass)
    {
        this.className = className;
        this.methods = methods;
        this.superClass = superClass;
    }

    public Token getClassName() {
        return className;
    }

    public List<Statement> getMethods() {
        return methods;
    }

    public Variable getSuperClass()
    {
        return superClass;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitClassStatement(this);
    }
}

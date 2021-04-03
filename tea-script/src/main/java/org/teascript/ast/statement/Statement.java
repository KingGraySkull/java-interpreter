package org.teascript.ast.statement;

import org.teascript.pattern.StatementVisitor;

public interface Statement
{
    <T> T accept(StatementVisitor<T> visitor);
}

package org.teascript.ast.expression;

import org.teascript.pattern.ExpressionVisitor;

public interface Expression
{
    <T> T accept(ExpressionVisitor<T> visitor);
}

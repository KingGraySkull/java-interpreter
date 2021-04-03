package org.teascript.pattern;

import org.teascript.ast.expression.*;

public interface ExpressionVisitor<T>
{
    T visitInfixExpression(Infix infix);

    T visitPrefixExpression(Prefix prefix);

    T visitLiteral(Literal literal);

    T visitGroupingExpression(Grouping grouping);

    T visitVariable(Variable variableExpression);

    T visitAssign(Assign assign);

    T visitLogical(Logical logical);

    T visitCall(Call call);

    T visitCompoundAssign(CompoundAssign compoundAssign);

    T visitArrayVariable(ArrayVariable arrayVariable);

    T visitGetProperty(GetProperty getProperty);

    T visitSetProperty(SetProperty setProperty);

    T visitThis(ThisExpression thisExpression);

    T visitSuper(Super superExpression);
}

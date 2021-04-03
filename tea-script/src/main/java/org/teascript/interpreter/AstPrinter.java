package org.teascript.interpreter;

import org.teascript.ast.expression.*;
import org.teascript.pattern.ExpressionVisitor;

public class AstPrinter implements ExpressionVisitor<String>
{
    public void interpret(Expression root)
    {
        System.out.println(root.accept(this));
    }

    @Override
    public String visitInfixExpression(Infix infix)
    {
        String leftExpression = infix.getLeft().accept(this);
        String operator = infix.getOperator().getLexeme().toString();
        String rightExpression = infix.getRight().accept(this);
        return " {"+leftExpression +" "+operator+" "+rightExpression+"} ";
    }

    @Override
    public String visitPrefixExpression(Prefix prefix)
    {
        String rightExpression = prefix.getRight().accept(this);
        String operator = prefix.getOperator().getLexeme().toString();
        return " {"+operator+" "+rightExpression+"} ";
    }

    @Override
    public String visitLiteral(Literal literal)
    {
        return literal.getValue().getLexeme().toString();
    }

    @Override
    public String visitGroupingExpression(Grouping grouping)
    {
        String expression = grouping.getExpression().accept(this);
        return " {"+expression+"} ";
    }

    @Override
    public String visitVariable(Variable variableExpression) {
        return null;
    }

    @Override
    public String visitAssign(Assign assign) {
        return null;
    }

    @Override
    public String visitLogical(Logical logical) {
        return null;
    }

    @Override
    public String visitCall(Call call)
    {
        return null;
    }

    @Override
    public String visitCompoundAssign(CompoundAssign compoundAssign)
    {
        return null;
    }

    @Override
    public String visitArrayVariable(ArrayVariable arrayVariable)
    {
        return null;
    }

    @Override
    public String visitGetProperty(GetProperty getProperty)
    {
        return null;
    }

    @Override
    public String visitSetProperty(SetProperty getProperty)
    {
        return null;
    }

    @Override
    public String visitThis(ThisExpression thisExpression)
    {
        return null;
    }

    @Override
    public String visitSuper(Super superExpression)
    {
        return null;
    }
}

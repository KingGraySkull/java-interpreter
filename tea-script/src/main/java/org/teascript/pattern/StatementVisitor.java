package org.teascript.pattern;

import org.teascript.ast.statement.*;

public interface StatementVisitor<T>
{
    T visitPrint(Print print);

    T visitExpressionStatement(ExpressionStatement expressionStatement);

    T visitVariableDeclarationStatement(VariableDeclarationStatement variableDeclarationStatement);

    T visitBlock(Block block);

    T visitIfElse(IfElse ifElse);

    T visitWhile(While whileStatement);

    T visitFor(For forStatement);

    T visitLoop(Loop loop);

    T visitFun(Fun fun);

    T visitReturn(ReturnStatement returnStatement);
    
    T visitArrayDeclaration(ArrayDeclaration arrayDeclaration);

    T visitClassStatement(ClassStatement classStatement);
}

package org.teascript.teaclass;

import org.teascript.constant.TokenType;
import org.teascript.exception.InterpreterException;
import org.teascript.function.FunObject;
import org.teascript.function.Function;
import org.teascript.interpreter.Environment;
import org.teascript.interpreter.Interpreter;
import org.teascript.lexer.Token;

import java.util.List;
import java.util.Map;

public class TeaClass implements Function
{
    private final String className;

    private final Map<String, FunObject> methods;

    private final TeaClass superClass;

    public TeaClass(String className, TeaClass superClass,Map<String, FunObject> methods)
    {
        this.className = className;
        this.superClass = superClass;
        this.methods = methods;
    }

    public String getClassName()
    {
        return className;
    }

    public FunObject findMethod(String name)
    {
        if (methods.containsKey(name))
        {
            return methods.get(name);
        }

        if (superClass != null)
        {
            return superClass.findMethod(name);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return className;
    }

    @Override
    public int arity()
    {
        FunObject initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments, Environment localEnv) throws InterpreterException
    {
        TeaScriptObject instance = new TeaScriptObject(this);
        localEnv.define(new Token(TokenType.THIS, "this", 0), instance);
        return instance;
    }
}

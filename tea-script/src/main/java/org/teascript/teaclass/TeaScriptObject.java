package org.teascript.teaclass;

import org.teascript.exception.InterpreterException;
import org.teascript.function.FunObject;
import org.teascript.lexer.Token;

import java.util.HashMap;
import java.util.Map;

public class TeaScriptObject
{
    private final Map<String, Object> fields = new HashMap<>();
    private TeaClass teaClass;

    public TeaScriptObject(TeaClass teaClass)
    {
        this.teaClass = teaClass;
    }

    public Object get(Token name)
    {
        if (fields.containsKey(name.getLexeme().toString()))
        {
            return fields.get(name.getLexeme().toString());
        }

        FunObject method = teaClass.findMethod(name.getLexeme().toString());
        if (method != null)
        {
            return method.bind(this);
        }

        throw new InterpreterException("Undefined property name :+ " + name.getLexeme().toString() + " at line no :: " + name.getLineNo());
    }

    public void set(Token name, Object value)
    {
        fields.put(name.getLexeme().toString(), value);
    }

    @Override
    public String toString()
    {
        return teaClass.toString() + " instance";
    }
}

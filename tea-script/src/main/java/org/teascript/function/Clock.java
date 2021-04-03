package org.teascript.function;

import org.teascript.interpreter.Environment;
import org.teascript.interpreter.Interpreter;

import java.util.List;

public class Clock implements Function
{

    @Override
    public int arity()
    {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments, Environment localEnv)
    {
        return (System.currentTimeMillis() / 1000);
    }

    @Override
    public String toString()
    {
        return "{native fun}<clock>";
    }
}

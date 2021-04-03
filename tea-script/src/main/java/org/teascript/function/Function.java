package org.teascript.function;

import org.teascript.exception.InterpreterException;
import org.teascript.interpreter.Environment;
import org.teascript.interpreter.Interpreter;

import java.util.List;

public interface Function
{
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments, Environment localEnv) throws InterpreterException;
}

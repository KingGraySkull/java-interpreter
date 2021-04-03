package org.teascript.exception;

public class InterpreterException extends RuntimeException
{
    public InterpreterException(String message)
    {
        super(message, null, false, false);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}

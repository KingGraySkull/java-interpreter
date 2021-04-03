package org.teascript.function;

import org.teascript.ast.statement.Block;
import org.teascript.ast.statement.Fun;
import org.teascript.constant.ObjectType;
import org.teascript.constant.TokenType;
import org.teascript.exception.InterpreterException;
import org.teascript.exception.Return;
import org.teascript.interpreter.Environment;
import org.teascript.interpreter.Interpreter;
import org.teascript.lexer.Token;
import org.teascript.teaclass.TeaScriptObject;

import java.util.List;

public class FunObject implements Function
{
    private final Fun declaration;

    private final Environment closure;

    private boolean isInitializer;

    public FunObject(Fun declaration, Environment closure, boolean isInitializer)
    {
        this.declaration = declaration;
        this.closure = closure;
        this.isInitializer = isInitializer;
    }

    @Override
    public int arity()
    {
        return declaration.getParameters().size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments, Environment localEnv) throws InterpreterException
    {
        localEnv.setClosure(this.closure);
        for (int i = 0; i < declaration.getParameters().size(); i++)
        {
            localEnv.define(declaration.getParameters().get(i), arguments.get(i));
        }
        Block body =  (Block)declaration.getBody();
        try
        {
            interpreter.evaluateBlockStatements(body.getStatements(), localEnv);
        }
        catch (Return e)
        {
            if (isInitializer)
            {
                return closure.getValue(new Token(TokenType.THIS, "this", 0));
            }
            return e.getValue();
        }

        if (this.isInitializer)
        {
            return closure.getValue(new Token(TokenType.THIS, "this", 0));
        }
        return null;
    }

    public FunObject bind(TeaScriptObject instance)
    {
        Environment environment = new Environment(closure);
        environment.define(new Token(TokenType.THIS, "this", 0), instance);
        return new FunObject(declaration, environment, this.isInitializer);
    }

    private Object parseReturnType(ObjectType type, Object returnValue)
    {
        Object value = null;
        if (type == ObjectType.INTEGER)
        {
            try
            {
                value = Integer.parseInt(returnValue.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Error : Expect integer as return type in function '"+declaration.getName().getLexeme()+"'");
            }
        }
        else if (type == ObjectType.FLOAT)
        {
            try
            {
                value = Float.parseFloat(returnValue.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Error : Expect float as return type in function '"+declaration.getName().getLexeme()+"'");
            }
        }
        else if (type == ObjectType.LONG)
        {
            try
            {
                value = Long.parseLong(returnValue.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Error : Expect long as return type in function '"+declaration.getName().getLexeme()+"'");
            }
        }
        else if (type == ObjectType.DOUBLE)
        {
            try
            {
                value = Double.parseDouble(returnValue.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Error : Expect double as return type in function '"+declaration.getName().getLexeme()+"'");
            }
        }
        else if (type == ObjectType.STRING)
        {
            if (!(returnValue instanceof String))
            {
                throw new InterpreterException("Error : Expect string as return type in function '"+declaration.getName().getLexeme()+"'");
            }
            else
            {
                value = returnValue.toString();
            }
        }
        else if (type == ObjectType.BOOLEAN)
        {
            if (!(returnValue instanceof Boolean))
            {
                throw new InterpreterException("Error : Expect boolean as return type in function '"+declaration.getName().getLexeme()+"'");
            }
            else
            {
                value = ((Boolean) returnValue).booleanValue();
            }
        }
        return value;
    }
}

package org.teascript.interpreter;

import org.teascript.exception.InterpreterException;
import org.teascript.lexer.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private final Map<String, Object> variableTable;

    private Environment enclosing;

    public Environment()
    {
        this.enclosing = null;
        variableTable = new HashMap<>();
    }

    public Environment(Environment enclosing)
    {
        this.enclosing = enclosing;
        variableTable = new HashMap<>();
    }

    public Environment getEnclosing()
    {
        return enclosing;
    }

    public void define(Token name, Object value)
    {

        String key = name.getLexeme().toString();
        if(!variableTable.containsKey(key))
        {
            variableTable.put(key, value);
        }
        else
        {
            throw new InterpreterException("Error redefining variable "+key+" at line no "+name.getLineNo());
        }
    }

    public Object getValue(Token name)
    {
        String key = name.getLexeme().toString();
        if (variableTable.containsKey(key))
        {
            return variableTable.get(key);
        }

        for (Environment current = this.enclosing; current != null; current = current.enclosing)
        {
            return current.getValue(name);
        }
        throw new InterpreterException("Error : Variable "+key+ " does not exist at line no : "+name.getLineNo());
    }

    public void assign(Token name, Object value)
    {
        String key = name.getLexeme().toString();
        if (variableTable.containsKey(key))
        {
            variableTable.put(key, value);
            return;
        }
        for (Environment current = this.enclosing; current != null; current = current.enclosing)
        {
            current.assign(name,value);
            return;
        }
        throw new InterpreterException("Error assigning variable "+key+" at line no : "+name.getLineNo()+". Variable does not exists");
    }

    public void setClosure(Environment closure)
    {
        this.enclosing = closure;
    }
}

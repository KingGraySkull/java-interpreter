package org.teascript.util;

import org.teascript.constant.ObjectType;
import org.teascript.exception.InterpreterException;

public class Calculator
{
    public static Object negate(Object rightValue, int lineNo)
    {
        Object result;
        if (rightValue instanceof Integer)
        {
            Integer value = Integer.parseInt(rightValue.toString());
            result = -value;
        }
        else if (rightValue instanceof Long)
        {
            Long value = Long.parseLong(rightValue.toString());
            result = -value;
        }
        else if (rightValue instanceof Float)
        {
            Float value = Float.parseFloat(rightValue.toString());
            result = -value;
        }
        else if (rightValue instanceof Double)
        {
            Double value = Double.parseDouble(rightValue.toString());
            result = -value;
        }
        else
        {
            throw new InterpreterException("Expect number after '-' at line no "+lineNo);
        }
        return result;
    }

    public static Object not(Object rightValue, int lineNo)
    {
        Object result = null;
        if (rightValue == null)
        {
            return false;
        }
        else if (rightValue instanceof Boolean)
        {
            Boolean value = Boolean.parseBoolean(rightValue.toString());
            result = !value;
        }
        else
        {
            throw new InterpreterException("Expect boolean expression after '!' at line no "+lineNo);
        }
        return result;
    }

    public static Object add(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if (lValue instanceof String || rValue instanceof String)
        {
            result = lValue.toString() + rValue.toString();
            return result;
        }
        if ((lValue instanceof Integer || lValue instanceof Long) && (rValue instanceof Integer || rValue instanceof Long))
        {
            result = Long.parseLong(lValue.toString()) + Long.parseLong(rValue.toString());
        }
        else if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) + Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for addition '+' operator at line no "+lineNo+". Expect number or string");
        }
        return result;
    }

    public static Object subtract(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if ((lValue instanceof Integer || lValue instanceof Long) && (rValue instanceof Integer || rValue instanceof Long))
        {
            result = Long.parseLong(lValue.toString()) - Long.parseLong(rValue.toString());
        }
        else if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) - Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '-' operator at line no "+lineNo);
        }
        return result;
    }

    public static Object multiply(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if ((lValue instanceof Integer || lValue instanceof Long) && (rValue instanceof Integer || rValue instanceof Long))
        {
            result = Long.parseLong(lValue.toString()) * Long.parseLong(rValue.toString());
        }
        else if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) * Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '*' operator at line no "+lineNo);
        }
        return result;
    }

    public static Object divide(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if ((lValue instanceof Integer || lValue instanceof Long) && (rValue instanceof Integer || rValue instanceof Long))
        {
            long rightValue =  Long.parseLong(rValue.toString());
            if (rightValue == 0)
            {
                throw new InterpreterException("Error : Cannot divide by zero at line no "+lineNo);
            }
            result = Long.parseLong(lValue.toString()) / rightValue;
        }
        else if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            double rightValue = Double.parseDouble(rValue.toString());
            if (rightValue == 0.0)
            {
                throw new InterpreterException("Error : Cannot divide by zero at line no "+lineNo);
            }
            result = Double.parseDouble(lValue.toString()) / rightValue;
        }
        else
        {
            throw new InterpreterException("Invalid operands for '/' operator at line no "+lineNo);
        }
        return result;
    }

    public static Object mod(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) % Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '%' operator at line no "+lineNo);
        }
        return result;
    }

    public static Object isGreater(Object lValue, Object rValue, int lineNo)
    {
        Object result = null;
        if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) > Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '>' operator at line no "+lineNo+".Operands should be numbers");
        }
        return result;
    }

    public static Object isGreaterEqual(Object lValue, Object rValue, int lineNo)
    {
        Object result;
        if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) >= Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '>=' operator at line no "+lineNo+".Operands should be numbers");
        }
        return result;
    }

    public static Object isLess(Object lValue, Object rValue, int lineNo)
    {
        Object result;
        if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) < Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '<' operator at line no "+lineNo+".Operands should be numbers");
        }
        return result;
    }

    public static Object isLessEqual(Object lValue, Object rValue, int lineNo)
    {
        Object result;
        if ((lValue instanceof Float || lValue instanceof Double || lValue instanceof Integer || lValue instanceof Long)
                && (rValue instanceof Float || rValue instanceof Double || rValue instanceof Integer || rValue instanceof Long))
        {
            result = Double.parseDouble(lValue.toString()) <= Double.parseDouble(rValue.toString());
        }
        else
        {
            throw new InterpreterException("Invalid operands for '<=' operator at line no "+lineNo+".Operands should be numbers");
        }
        return result;
    }

    public static Object isEqual(Object lValue, Object rValue, int lineNo)
    {
        if (lValue == null && rValue == null)
        {
            return true;
        }
        if (rValue == null)
        {
            return false;
        }
        return lValue.equals(rValue);
    }

    public static Object isNotEqual(Object lValue, Object rValue, int lineNo)
    {
        if (lValue == null && rValue == null)
        {
            return true;
        }
        return !lValue.equals(rValue);
    }

    public static boolean isTruthy(Object value)
    {
        if (value == null)
        {
            return false;
        }
        else if (value instanceof Boolean)
        {
            return Boolean.parseBoolean(value.toString());
        }
        return false;
    }

    public static Object getTypeInference(ObjectType type, Object value, String name) throws InterpreterException
    {
        if (type == ObjectType.INTEGER)
        {
            try
            {
                value = Integer.parseInt(value.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Expect integer type for variable '"+name+"'.");
            }
        }
        else if (type == ObjectType.DOUBLE)
        {
            try
            {
                value = Double.parseDouble(value.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Expect double type for variable '"+name+"'.");
            }
        }
        else if (type == ObjectType.FLOAT)
        {
            try
            {
                value = Float.parseFloat(value.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Expect float type for variable '"+name+"'.");
            }
        }
        else if (type == ObjectType.LONG)
        {
            try
            {
                value = Long.parseLong(value.toString());
            }
            catch (NumberFormatException e)
            {
                throw new InterpreterException("Expect float type for variable '"+name+"'.");
            }
        }
        else if (type == ObjectType.BOOLEAN)
        {
            if (!(value instanceof Boolean))
            {
                throw new InterpreterException("Expect boolean type for variable '"+name+"'.");
            }
        }
        return value;
    }
}

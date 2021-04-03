package org.teascript.interpreter;

import org.teascript.ast.expression.*;
import org.teascript.ast.statement.*;
import org.teascript.constant.ObjectType;
import org.teascript.constant.TokenType;
import org.teascript.exception.InterpreterException;
import org.teascript.exception.Return;
import org.teascript.function.Clock;
import org.teascript.function.FunObject;
import org.teascript.function.Function;
import org.teascript.lexer.Token;
import org.teascript.pattern.ExpressionVisitor;
import org.teascript.pattern.StatementVisitor;
import org.teascript.teaclass.TeaClass;
import org.teascript.teaclass.TeaScriptObject;
import org.teascript.util.Calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter implements ExpressionVisitor<Object>, StatementVisitor<Void>
{
    public final Environment globals = new Environment();
    private Environment environment = globals;

    public Interpreter()
    {
        globals.define(new Token(TokenType.IDENTIFIER, "clock", 0), new Clock());
    }

    public void interpret(List<Statement> statements)
    {
        for (Statement statement : statements)
        {
            statement.accept(this);
        }
    }

    private Object evaluateExpression(Expression expression)
    {
        try
        {
            return expression.accept(this);
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public void evaluateStatement(Statement statement)
    {
        try
        {
            statement.accept(this);
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public Object visitInfixExpression(Infix infix)
    {
        Object left = infix.getLeft().accept(this);
        Object right = infix.getRight().accept(this);
        TokenType operatorType = infix.getOperator().getTokenType();
        int lineNo = infix.getOperator().getLineNo();
        switch (operatorType)
        {
            case PLUS:
                return Calculator.add(left, right, lineNo);
            case MINUS:
                return Calculator.subtract(left, right, lineNo);
            case SLASH:
                return Calculator.divide(left, right, lineNo);
            case STAR:
                return Calculator.multiply(left, right, lineNo);
            case MODULO:
                return Calculator.mod(left, right, lineNo);
            case EQUAL_EQUAL:
                return Calculator.isEqual(left, right, lineNo);
            case NOT_EQUAL:
                return Calculator.isNotEqual(left, right, lineNo);
            case LESS:
                return Calculator.isLess(left, right, lineNo);
            case LESS_EQUAL:
                return Calculator.isLessEqual(left, right, lineNo);
            case GREATER:
                return Calculator.isGreater(left, right, lineNo);
            case GREATER_EQUAL:
                return Calculator.isGreaterEqual(left, right, lineNo);
        }
        return null;
    }

    @Override
    public Object visitPrefixExpression(Prefix prefix)
    {
        Object right = prefix.getRight().accept(this);
        TokenType operatorType = prefix.getOperator().getTokenType();
        int lineNo = prefix.getOperator().getLineNo();
        switch (operatorType)
        {
            case MINUS:
                return Calculator.negate(right, lineNo);
            case NOT:
                return Calculator.not(right, lineNo);
        }
        return null;
    }

    @Override
    public Object visitLiteral(Literal literal)
    {
        return literal.getValue().getLexeme();
    }

    @Override
    public Object visitGroupingExpression(Grouping grouping)
    {
        return grouping.getExpression().accept(this);
    }

    @Override
    public Void visitPrint(Print print)
    {
        Object value = evaluateExpression(print.getExpression());
        if (print.isNewLine())
        {
            if (value != null)
            {
                System.out.println(value.toString().replace("\\n", System.lineSeparator()));
            }
            else
            {
                System.out.println(""+null);
            }
        }
        else
        {
            if (value != null)
            {
                System.out.printf("%s", value.toString().replace("\\n", System.lineSeparator()));
            }
            else
            {
                System.out.print(""+null);
            }
        }
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatement expressionStatement)
    {
        evaluateExpression(expressionStatement.getExpression());
        return null;
    }

    @Override
    public Void visitVariableDeclarationStatement(VariableDeclarationStatement variableDeclarationStatement)
    {
        Object value = null;
        String variableName = variableDeclarationStatement.getIdentifierName().getLexeme().toString();
        if (variableDeclarationStatement.getInitializer() != null)
        {
            value = evaluateExpression(variableDeclarationStatement.getInitializer());
            //handle type of variable here
            ObjectType type = variableDeclarationStatement.getDataType();
            value = Calculator.getTypeInference(type, value, variableName);

            if (type == ObjectType.STRING)
            {
                if (!(value instanceof String))
                {
                    throw new InterpreterException("Error : Expected type is string for variable '"+variableName+"'");
                }
            }
        }
        environment.define(variableDeclarationStatement.getIdentifierName(), value);
        return null;
    }

    @Override
    public Object visitVariable(Variable variableExpression)
    {
        try
        {
            return environment.getValue(variableExpression.getName());
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public Object visitAssign(Assign assign)
    {
        Token variableName = null;
        if (assign.getlValue() instanceof  ArrayVariable)
        {
            ArrayVariable arrayVariable = (ArrayVariable) assign.getlValue();
            if (arrayVariable.getArrayIndex() == null)
            {
                Object value = evaluateExpression(assign.getrValue());
                environment.assign(arrayVariable.getName(), value);
                return value;
            }
            else
            {
                Object indexValue = evaluateExpression(arrayVariable.getArrayIndex());
                int index = -1;
                try
                {
                    index = Integer.parseInt(indexValue.toString());
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error : Expect integer index in line no '"+arrayVariable.getName().getLineNo()+"'.");
                    System.exit(-1);
                }

                if (index >=0)
                {
                    Object[] objects = (Object[]) environment.getValue(arrayVariable.getName());
                    Object rightValue =  evaluateExpression(assign.getrValue());
                    if (index < objects.length)
                    {
                        objects[index] = rightValue;
                    }
                    else
                    {
                        throw new InterpreterException("Error : Index out of bound for array '"+arrayVariable.getName().getLexeme().toString()+"'");
                    }

                    environment.assign(arrayVariable.getName(), objects);
                    return rightValue;
                }
                else
                {
                    System.out.println("Error : Expect integer index in line no '"+arrayVariable.getName().getLineNo()+"'.");
                    System.exit(-1);
                }
            }
        }
        else if (assign.getlValue() instanceof Variable)
        {
            Variable lValue = assign.getlValue();
            variableName = lValue.getName();
            Object rValue = evaluateExpression(assign.getrValue());
            try
            {
                environment.assign(variableName, rValue);
            }
            catch (InterpreterException e)
            {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
            return rValue;
        }
        return null;
    }

    @Override
    public Object visitCompoundAssign(CompoundAssign compoundAssign)
    {
        Token variableName = compoundAssign.getlValue().getName();
        Object lValue = evaluateExpression(compoundAssign.getlValue());
        Object rValue = evaluateExpression(compoundAssign.getrValue());
        TokenType operatorType = compoundAssign.getOperator().getTokenType();
        int lineNo = compoundAssign.getOperator().getLineNo();
        Object result = null;

        switch (operatorType)
        {
            case PLUS_ASSIGN:
                result = Calculator.add(lValue, rValue, lineNo);
                break;
            case MINUS_ASSIGN:
                result = Calculator.subtract(lValue, rValue, lineNo);
                break;
            case SLASH_ASSIGN:
                result = Calculator.divide(lValue, rValue, lineNo);
                break;
            case STAR_ASSIGN:
                result = Calculator.multiply(lValue, rValue, lineNo);
                break;
            case MODULO_ASSIGN:
                result = Calculator.mod(lValue, rValue, lineNo);
                break;
            default:
                throw new InterpreterException("Unknown operator '"+compoundAssign.getOperator().getLexeme()+"' at line no '"+lineNo+"'.");
        }

        try
        {
            environment.assign(variableName, result);
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return result;
    }

    @Override
    public Object visitArrayVariable(ArrayVariable arrayVariable)
    {
        try
        {
            if (arrayVariable.getArrayIndex() == null)
            {
                return environment.getValue(arrayVariable.getName());
            }
            else
            {
                Object index = evaluateExpression(arrayVariable.getArrayIndex());
                int arrayIndex = -1;
                try
                {
                    arrayIndex = Integer.parseInt(index.toString());
                }
                catch (NumberFormatException e)
                {
                    throw new InterpreterException("Expect an integer index");
                }
                Object[] arrayObject = ( Object[] ) environment.getValue(arrayVariable.getName());
                if (arrayIndex >= arrayObject.length)
                {
                    throw  new InterpreterException("Error :: index "+arrayIndex+" out of bound for array "+arrayVariable.getName().getLexeme().toString()+".");
                }
                else
                {
                    return arrayObject[arrayIndex];
                }
            }
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public Object visitLogical(Logical logical)
    {
        Object left = evaluateExpression(logical.getLeft());
        TokenType type = logical.getOperator().getTokenType();
        if (type == TokenType.LOGICAL_OR)
        {
            if (Calculator.isTruthy(left))
            {
                return left;
            }
        }
        else
        {
            if (!Calculator.isTruthy(left))
            {
                return left;
            }
        }
        return evaluateExpression(logical.getRight());
    }

    @Override
    public Object visitCall(Call call)
    {
        Object callee = evaluateExpression(call.getCallee());
        List<Object> arguments = new ArrayList<>();
        for (Expression argument : call.getArguments())
        {
            arguments.add(evaluateExpression(argument));
        }

        if (!(callee instanceof Function))
        {
            throw new InterpreterException("Can only call functions.");
        }


        Function callable = (Function) callee;
        if(arguments.size() != callable.arity())
        {
            throw new InterpreterException("Expected "+callable.arity()+"  arguments but got "+arguments.size()+".");
        }
        Environment previousEnv = this.environment;
        Object returnValue = null;
        try
        {
            returnValue = callable.call(this, arguments, new Environment(previousEnv));
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        this.environment = previousEnv;
        return returnValue;
    }

    @Override
    public Void visitBlock(Block block)
    {
        Environment previous = this.environment;
        evaluateBlockStatements(block.getStatements(), new Environment(environment));
        this.environment = previous;
        return null;
    }

    @Override
    public Void visitIfElse(IfElse ifElse)
    {
        Object value = evaluateExpression(ifElse.getCondition());
        if (Calculator.isTruthy(value))
        {
            evaluateStatement(ifElse.getThen());
        }
        else if (ifElse.getElseStatement() != null)
        {
            evaluateStatement(ifElse.getElseStatement());
        }
        return null;
    }

    @Override
    public Void visitWhile(While whileStatement)
    {
        while (Calculator.isTruthy(evaluateExpression(whileStatement.getCondition())))
        {
            evaluateStatement(whileStatement.getStatement());
        }
        return null;
    }

    @Override
    public Void visitFor(For forStatement)
    {
        if (forStatement.getInitializer() != null)
        {
            evaluateStatement(forStatement.getInitializer());
        }

        while (Calculator.isTruthy(evaluateExpression(forStatement.getCondition())))
        {
            evaluateStatement(forStatement.getBody());
            evaluateExpression(forStatement.getIterator());
        }
        return null;
    }

    @Override
    public Void visitLoop(Loop loop)
    {
        Object value = evaluateExpression(loop.getRange());
        Object step  = evaluateExpression(loop.getIterator());
        double i = 1;
        if (value != null)
        {
            double range = Double.parseDouble(value.toString());
            double iterator  =  Double.parseDouble(step.toString());

            while (i <= range)
            {
                evaluateStatement(loop.getBody());
                i += iterator;
            }
        }
        return null;
    }

    @Override
    public Void visitFun(Fun fun)
    {
        FunObject funObject = new FunObject(fun, this.environment, false);
        environment.define(fun.getName(),funObject);
        return null;
    }

    @Override
    public Void visitClassStatement(ClassStatement classStatement) {

        Object superClass = null;

        if (classStatement.getSuperClass() != null)
        {
            superClass = evaluateExpression(classStatement.getSuperClass());
            if (!(superClass instanceof TeaClass))
            {
                throw new InterpreterException("Error : Superclass "+classStatement.getSuperClass().getName().getLexeme().toString()+"must be a class at line no "+classStatement.getSuperClass().getName().getLineNo());
            }
        }

        environment.define(classStatement.getClassName(), null);

        if (classStatement.getSuperClass() != null)
        {
            environment = new Environment(environment);
            environment.define(new Token(TokenType.SUPER,"super", 0), superClass);
        }

        Map<String, FunObject> methods = new HashMap<>();
        for (Statement method : classStatement.getMethods())
        {
            Fun fun = (Fun) method;
            FunObject function = new FunObject(fun, environment, fun.getName().getLexeme().toString().equals("init"));
            methods.put(fun.getName().getLexeme().toString(), function);
         }

        TeaClass teaClass = new TeaClass(classStatement.getClassName().getLexeme().toString(), (TeaClass)superClass,methods);

        if (superClass != null)
        {
            environment = environment.getEnclosing();
        }
        environment.assign(classStatement.getClassName(), teaClass);
        return null;
    }

    @Override
    public Object visitSuper(Super superExpression)
    {
        try
        {
           Object superClass = environment.getValue(superExpression.getKeyword());
           if (superClass instanceof TeaClass)
           {
               FunObject funObject = ((TeaClass)superClass).findMethod(superExpression.getMethod().getLexeme().toString());
               if (funObject == null) {
                   throw  new InterpreterException("Undefined property '" + superExpression.getMethod().getLexeme().toString() + "'.");
               }
               Object instance = environment.getValue(new Token(TokenType.THIS, "this", 0));
               if (instance instanceof TeaScriptObject)
               {
                   return funObject.bind((TeaScriptObject)instance);
               }
               else
               {
                   throw new InterpreterException("only methods can be called in super expression");
               }
           }
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public Object visitGetProperty(GetProperty getProperty)
    {
        Object object = evaluateExpression(getProperty.getObject());
        if (object instanceof TeaScriptObject)
        {
            return ((TeaScriptObject) object).get(getProperty.getName());
        }
        throw new InterpreterException("Only instances have properties.");
    }

    @Override
    public Object visitSetProperty(SetProperty setProperty)
    {
        Object object = evaluateExpression(setProperty.getObject());

        if (!(object instanceof TeaScriptObject))
        {
            throw new InterpreterException("Only instances have fields.");
        }

        Object value = evaluateExpression(setProperty.getRightExpression());
        ((TeaScriptObject) object).set(setProperty.getName(), value);
        return value;
    }

    @Override
    public Object visitThis(ThisExpression thisExpression)
    {
        try
        {
            return environment.getValue(thisExpression.getKeyword());
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public Void visitReturn(ReturnStatement returnStatement)
    {
        Object value = null;
        if (returnStatement.getValue() != null)
        {
            value = evaluateExpression(returnStatement.getValue());
        }
        throw new Return(value);
    }

    @Override
    public Void visitArrayDeclaration(ArrayDeclaration arrayDeclaration)
    {
        Object[] objects = null;
        if (arrayDeclaration.getList() == null)
        {
            objects = new Object[arrayDeclaration.getSize()];
            try
            {
                environment.define(arrayDeclaration.getName(), objects);
            }
            catch (InterpreterException e)
            {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        else
        {
            objects = new Object[arrayDeclaration.getSize()];
            for (int i = 0; i < arrayDeclaration.getList().size(); i++)
            {
                Object value = evaluateExpression(arrayDeclaration.getList().get(i));
                objects[i] = value;
            }
            try
            {
                environment.define(arrayDeclaration.getName(), objects);
            }
            catch (InterpreterException e)
            {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        return null;
    }

    public void evaluateBlockStatements(List<Statement> statements, Environment local)
    {
        this.environment = local;
        for (Statement current : statements)
        {
            current.accept(this);
        }
    }

}

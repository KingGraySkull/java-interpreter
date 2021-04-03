package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Variable;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class VariableExpressionParser implements PrefixParser
{
    @Override
    public Expression parse(Token token, PrecedenceParser parser) throws ParserException
    {
        return new Variable(token);
    }
}

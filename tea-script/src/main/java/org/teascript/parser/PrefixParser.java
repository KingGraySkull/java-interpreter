package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public interface PrefixParser
{
    Expression parse(Token token, PrecedenceParser parser) throws ParserException;
}

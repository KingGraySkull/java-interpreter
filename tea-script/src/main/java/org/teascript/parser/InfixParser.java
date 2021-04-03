package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public interface InfixParser
{
    Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException;
}

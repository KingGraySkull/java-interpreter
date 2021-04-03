package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.constant.TokenType;

public interface Parser
{
    public Expression parse();

    public Expression parseExpression(int precedence);

    public void consume(TokenType expected, String message);

    public void lookAhead();
}

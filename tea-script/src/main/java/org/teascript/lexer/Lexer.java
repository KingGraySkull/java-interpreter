package org.teascript.lexer;

import org.teascript.constant.TokenType;

import java.util.List;

public interface Lexer
{
    List<Token> getTokens();
}

package org.teascript.lexer;

import org.teascript.constant.TokenType;

public interface Lexeme
{
    void setLexeme(Object lexeme);

    Object getLexeme();
}

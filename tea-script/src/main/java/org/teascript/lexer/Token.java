package org.teascript.lexer;

import org.teascript.constant.TokenType;

public class Token implements Lexeme
{
    private TokenType type;

    private Object lexeme;

    private final int lineNo;

    public Token(TokenType type, Object lexeme, int lineNo)
    {
        this.type = type;
        this.lexeme = lexeme;
        this.lineNo = lineNo;
    }

    @Override
    public void setLexeme(Object lexeme)
    {
        this.lexeme = lexeme;
    }

    @Override
    public Object getLexeme()
    {
        return this.lexeme;
    }

    public void setTokenType(TokenType type)
    {
        this.type = type;
    }

    public TokenType getTokenType()
    {
        return this.type;
    }

    public int getLineNo()
    {
        return this.lineNo;
    }
}

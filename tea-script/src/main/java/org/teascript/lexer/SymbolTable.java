package org.teascript.lexer;

import org.teascript.constant.ObjectType;
import org.teascript.constant.TokenType;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable
{
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    private static final Map<TokenType, ObjectType> OBJECT_TYPE = new HashMap<>();
    static
    {
        KEYWORDS.put("class", TokenType.CLASS);
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);
        KEYWORDS.put("this", TokenType.THIS);
        KEYWORDS.put("for", TokenType.FOR);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("loop", TokenType.LOOP);
        KEYWORDS.put("return", TokenType.RETURN);
        KEYWORDS.put("var", TokenType.VAR);
        KEYWORDS.put("fun", TokenType.FUN);
        KEYWORDS.put("void", TokenType.VOID);
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("null", TokenType.NULL);
        KEYWORDS.put("print", TokenType.PRINT);
        KEYWORDS.put("println", TokenType.PRINT_LN);
        KEYWORDS.put("int", TokenType.INT);
        KEYWORDS.put("long", TokenType.LONG);
        KEYWORDS.put("float", TokenType.FLOAT);
        KEYWORDS.put("double", TokenType.DOUBLE);
        KEYWORDS.put("string", TokenType.STRING);
        KEYWORDS.put("switch", TokenType.SWITCH);
        KEYWORDS.put("case", TokenType.CASE);
        KEYWORDS.put("break", TokenType.BREAK);
        KEYWORDS.put("continue", TokenType.CONTINUE);
        KEYWORDS.put("bool", TokenType.BOOL);
        KEYWORDS.put("super", TokenType.SUPER);
        KEYWORDS.put("extends", TokenType.EXTENDS);

        OBJECT_TYPE.put(TokenType.INT, ObjectType.INTEGER);
        OBJECT_TYPE.put(TokenType.LONG, ObjectType.LONG);
        OBJECT_TYPE.put(TokenType.FLOAT, ObjectType.FLOAT);
        OBJECT_TYPE.put(TokenType.DOUBLE, ObjectType.DOUBLE);
        OBJECT_TYPE.put(TokenType.STRING, ObjectType.STRING);
        OBJECT_TYPE.put(TokenType.BOOL, ObjectType.BOOLEAN);
        OBJECT_TYPE.put(TokenType.IDENTIFIER, ObjectType.OBJECT);
    }

    public static TokenType getKeywordType(String key)
    {
        return KEYWORDS.get(key);
    }

    public static ObjectType getObjectType(TokenType type)
    {
        return OBJECT_TYPE.get(type);
    }
}

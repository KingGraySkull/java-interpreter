package org.teascript.parser;

import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;

import java.util.HashMap;
import java.util.Map;

public class PrecedenceTable
{
    private static final Map<TokenType, Integer> PRECEDENCE_TABLE = new HashMap<>();

    static
    {
        PRECEDENCE_TABLE.put(TokenType.RIGHT_PAREN, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LEFT_PAREN, Precedence.CALL.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LEFT_SQUARE_BRACKET, Precedence.CALL.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.RIGHT_SQUARE_BRACKET, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.DOT, Precedence.CALL.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.SEMICOLON, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.EOF, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.EQUAL, Precedence.ASSIGNMENT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.PLUS, Precedence.SUM.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.MINUS, Precedence.SUM.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.SLASH, Precedence.PRODUCT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.STAR, Precedence.PRODUCT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.MODULO, Precedence.PRODUCT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.NOT, Precedence.PREFIX.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.NEGATE, Precedence.PREFIX.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.INCREMENT, Precedence.PREFIX.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.DECREMENT, Precedence.PREFIX.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.NOT_EQUAL, Precedence.EQUALITY.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.EQUAL_EQUAL, Precedence.EQUALITY.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.GREATER, Precedence.COMPARISON.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.GREATER_EQUAL, Precedence.COMPARISON.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LESS, Precedence.COMPARISON.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LESS_EQUAL, Precedence.COMPARISON.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.STRING, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.IDENTIFIER, Precedence.NONE.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LOGICAL_AND, Precedence.AND.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.LOGICAL_OR, Precedence.OR.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.PLUS_ASSIGN, Precedence.SUM.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.MINUS_ASSIGN, Precedence.SUM.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.SLASH_ASSIGN, Precedence.PRODUCT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.STAR_ASSIGN, Precedence.PRODUCT.getPrecedence());
        PRECEDENCE_TABLE.put(TokenType.MODULO_ASSIGN, Precedence.PRODUCT.getPrecedence());
    }

    public static Integer getPrecedence(TokenType type)
    {
        Integer precedence = PRECEDENCE_TABLE.get(type);
        if (precedence == null )
        {
            return 0;
        }
        return precedence;
    }
}

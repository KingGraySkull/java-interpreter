package org.teascript.parser;

import org.teascript.constant.TokenType;

import java.util.HashMap;
import java.util.Map;

public class ParserTable
{
    private static final Map<TokenType, PrefixParser> PREFIX_OPERATOR_TABLE = new HashMap<>();

    private static final Map<TokenType, InfixParser> INFIX_OPERATOR_TABLE = new HashMap<>();

    static
    {
        registerPrefixOperators();
        registerInfixOperators();
    }

    public static void registerPrefixOperators()
    {
        PREFIX_OPERATOR_TABLE.put(TokenType.MINUS, new PrefixExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.NOT, new PrefixExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.INCREMENT, new PrefixExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.DECREMENT, new PrefixExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.NUMBER, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.TEXT, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.TRUE, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.FALSE, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.NULL, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.THIS, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.SUPER, new LiteralExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.IDENTIFIER, new VariableExpressionParser());
        PREFIX_OPERATOR_TABLE.put(TokenType.LEFT_PAREN, new GroupingExpressionParser());
    }

    public static void registerInfixOperators()
    {
        INFIX_OPERATOR_TABLE.put(TokenType.PLUS, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.MINUS, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.STAR, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.SLASH, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.MODULO, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.EQUAL_EQUAL, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.NOT_EQUAL, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LESS, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LESS_EQUAL, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.GREATER, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.GREATER_EQUAL, new InfixExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.EQUAL, new AssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LOGICAL_AND, new LogicalExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LOGICAL_OR, new LogicalExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LEFT_PAREN, new CallExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.LEFT_SQUARE_BRACKET, new ArrayExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.PLUS_ASSIGN, new CompoundAssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.MINUS_ASSIGN, new CompoundAssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.SLASH_ASSIGN, new CompoundAssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.STAR_ASSIGN, new CompoundAssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.MODULO_ASSIGN, new CompoundAssignExpressionParser());
        INFIX_OPERATOR_TABLE.put(TokenType.DOT, new ObjectExpressionParser());
    }

    public static PrefixParser getPrefixParser(TokenType type)
    {
        return PREFIX_OPERATOR_TABLE.get(type);
    }

    public static InfixParser getInfixParser(TokenType type)
    {
        return INFIX_OPERATOR_TABLE.get(type);
    }
}

package org.teascript.constant;

public enum TokenType {
    // Single-character tokens.
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    SLASH,
    STAR,
    COLON,
    MODULO,
    RAISE_TO,
    LEFT_SQUARE_BRACKET,
    RIGHT_SQUARE_BRACKET,

    // One or two character tokens.
    NOT,
    NOT_EQUAL,
    EQUAL,
    EQUAL_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    INCREMENT,
    DECREMENT,
    PLUS_ASSIGN,
    MINUS_ASSIGN,
    STAR_ASSIGN,
    SLASH_ASSIGN,
    LOGICAL_AND,
    LOGICAL_OR,
    MODULO_ASSIGN,
    NEGATE,
    POST_INCREMENT,
    POST_DECREMENT,

    // Literals.
    IDENTIFIER,
    NUMBER,
    TEXT,

    // Keywords.
    AND,
    CLASS,
    VOID,
    FUN,
    ELSE,
    FALSE,
    FOR,
    IF,
    NULL,
    OR,
    PRINT,
    PRINT_LN,
    RETURN,
    SUPER,
    THIS,
    TRUE,
    VAR,
    WHILE,
    LOOP,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BOOL,
    SWITCH,
    STRING,
    CASE,
    BREAK,
    CONTINUE,
    EXTENDS,

    ERROR,
    EOF
}
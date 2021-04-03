package org.teascript.constant;

public enum Precedence
{
    NONE(0),
    ASSIGNMENT(2), // =
    OR(3),
    AND(4),
    EQUALITY(5), // == !=
    COMPARISON(6), // < > <= >=
    SUM(7), // + -
    PRODUCT(8), // / * %
    EXPONENT(9),
    PREFIX(10), // ! - ++ --
    POSTFIX(11), // ! -
    CALL(12); // . () []

    private final int precedence;

    Precedence(int precedence) //private by default
    {
        this.precedence = precedence;
    }

    public int getPrecedence() {
        return this.precedence;
    }
}

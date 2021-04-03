package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Infix;
import org.teascript.constant.Precedence;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class InfixExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        int precedence = 0;
        switch (operator.getTokenType())
        {
            case PLUS:
            case MINUS:
                precedence = Precedence.SUM.getPrecedence();
                break;
            case STAR:
            case SLASH:
            case MODULO:
                precedence = Precedence.PRODUCT.getPrecedence();
                break;
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
                precedence = Precedence.COMPARISON.getPrecedence();
                break;
            case EQUAL_EQUAL:
            case NOT_EQUAL:
                precedence = Precedence.EQUALITY.getPrecedence();
                break;
            case EQUAL:
                precedence = Precedence.ASSIGNMENT.getPrecedence();
                break;
            default:
                throw new ParserException("Unidentified token '"+operator.getLexeme()+"' at line no "+operator.getLineNo());
        }
        Expression rightOperand = parser.parseExpression(precedence);
        return new Infix(leftOperand, operator, rightOperand);
    }
}

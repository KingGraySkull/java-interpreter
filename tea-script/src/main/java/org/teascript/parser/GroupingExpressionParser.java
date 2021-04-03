package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Grouping;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class GroupingExpressionParser implements PrefixParser
{
    @Override
    public Expression parse(Token token, PrecedenceParser parser) throws ParserException
    {
        Expression rightExpression = parser.parseExpression(Precedence.NONE.getPrecedence() + 1);
        parser.consume(TokenType.RIGHT_PAREN, "Expected ')' at line no :: "+token.getLineNo());
        return new Grouping(rightExpression);
    }
}

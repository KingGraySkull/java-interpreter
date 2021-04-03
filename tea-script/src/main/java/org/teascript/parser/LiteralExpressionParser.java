package org.teascript.parser;

import org.teascript.ast.expression.Expression;
import org.teascript.ast.expression.Literal;
import org.teascript.ast.expression.Super;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

public class LiteralExpressionParser implements PrefixParser
{
    @Override
    public Expression parse(Token token, PrecedenceParser parser) throws ParserException
    {
        if (token.getTokenType() == TokenType.SUPER)
        {
            parser.consume(TokenType.DOT, "Expect '.' after 'super'.");
            parser.consume(TokenType.IDENTIFIER, "Expect superclass method name.");
            return new Super(token, parser.previousToken);
        }
        return new Literal(token);
    }
}

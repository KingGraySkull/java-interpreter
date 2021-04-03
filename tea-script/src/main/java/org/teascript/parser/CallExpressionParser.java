package org.teascript.parser;

import org.teascript.ast.expression.Call;
import org.teascript.ast.expression.Expression;
import org.teascript.constant.Precedence;
import org.teascript.constant.TokenType;
import org.teascript.exception.ParserException;
import org.teascript.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class CallExpressionParser implements InfixParser
{
    @Override
    public Expression parse(PrecedenceParser parser, Expression leftOperand, Token operator) throws ParserException
    {
        List<Expression> arguments = new ArrayList<>();
        while (!parser.isEnd())
        {
            if (!parser.check(TokenType.RIGHT_PAREN))
            {
                do
                {
                    if (arguments.size() >= 255)
                    {
                        throw new ParserException("Function cannot have more than 255 arguments.");
                    }
                    arguments.add(parser.parseExpression(Precedence.NONE.getPrecedence() + 1));
                } while (parser.match(TokenType.COMMA));
            }
            else
            {
                break;
            }
        }
        parser.consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments.");
        return new Call(leftOperand, operator, arguments);
    }
}

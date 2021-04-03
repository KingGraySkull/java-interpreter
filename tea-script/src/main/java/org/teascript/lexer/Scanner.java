package org.teascript.lexer;

import org.teascript.constant.TokenType;
import org.teascript.exception.LexicalException;
import org.teascript.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Scanner implements Lexer
{
    private char currentCharacter;

    private int currentCharPos;

    private int currentLineNo;

    private final String sourceCode;

    private final int srcCodeSize;

    private final List<Token> tokens;

    private final List<String> errorMessages;

    public Scanner(String sourceCode)
    {
        this.sourceCode = sourceCode;
        tokens = new ArrayList<>();
        this.currentLineNo = 1;
        this.currentCharPos = 0;
        this.srcCodeSize = sourceCode.length();
        errorMessages = new ArrayList<>();
    }

    @Override
    public List<Token> getTokens()
    {
        while (!isEnd())
        {
           try
           {
                tokens.add(scanToken());
           }
           catch (LexicalException e)
           {
               errorMessages.add(e.getMessage());
           }
        }
        tokens.add(buildToken(TokenType.EOF,'\0'));
        return tokens;
    }

    private void lookAhead()
    {
        if(!isEnd())
        {
            currentCharacter = sourceCode.charAt(currentCharPos);
            currentCharPos++;
        }
    }

    private char peek()
    {
        if (!isEnd())
        {
            return sourceCode.charAt(currentCharPos);
        }
        return '\0';
    }

    private boolean match(char expected)
    {
        if (peek() == expected)
        {
            lookAhead();
            return true;
        }
        return false;
    }

    //skip whitespace character, handle new line, skip comments
    private void skipWhiteSpace()
    {
        while (true)
        {
            if (isEnd())
            {
                break;
            }
            else
            {
                switch (currentCharacter)
                {
                    case ' ':
                    case '\r':
                    case '\t':
                        lookAhead();
                        break;
                    case '/':
                        if (match('/'))
                        {
                            while (peek() != '\n' && !isEnd())
                            {
                                lookAhead();
                            }
                        }
                        else
                        {
                            return;
                        }
                        break;
                    case '\n':
                        currentLineNo++;
                        lookAhead();
                        break;
                    default:
                        return;
                }
            }
        }//end while
    }

    private Token buildToken(TokenType type, Object lexeme)
    {
        return new Token(type, lexeme, currentLineNo);
    }

    private String extractLexeme(int start, int end)
    {
        return sourceCode.substring(start, end);
    }

    private Token buildToken(TokenType type)
    {
        String lexeme = extractLexeme(currentCharPos - 2, currentCharPos);
        return new Token(type, lexeme, currentLineNo);
    }

    private Token buildNumber()
    {
        int startPos = currentCharPos - 1;
        while (Character.isDigit(peek()))
        {
            this.currentCharPos++;
        }
        if (peek() == '.')
        {
            this.currentCharPos++;
            while (Character.isDigit(peek()))
            {
                this.currentCharPos++;
            }
            String realNumber = extractLexeme(startPos,currentCharPos);
            return buildToken(TokenType.NUMBER, Double.valueOf(realNumber));
        }
        String integerNumber =  extractLexeme(startPos,currentCharPos);
        return buildToken(TokenType.NUMBER, Long.parseLong(integerNumber));
    }

    private Token buildIdentifier()
    {
        int starPos = currentCharPos - 1;
        while ( isValidIdentifierCharacter(peek()) )
        {
            currentCharPos++;
        }
        String identifierValue = sourceCode.substring(starPos, currentCharPos);
        TokenType type = SymbolTable.getKeywordType(identifierValue);
        return  buildToken((type == null ? TokenType.IDENTIFIER : type), identifierValue);
    }

    private Token buildString()
    {
        int startPos = currentCharPos;
        while (peek() != '"' && !isEnd())
        {
            if (peek() == '\n')
            {
                currentLineNo++;
            }
            currentCharPos++;
        }
        if (isEnd())
        {
            return buildToken(TokenType.ERROR, "Unterminated String.");
        }
        currentCharPos++;
        String stringValue = sourceCode.substring(startPos, currentCharPos - 1);
        return buildToken(TokenType.TEXT, stringValue);
    }

    private Token scanToken() throws LexicalException
    {
        lookAhead();
        skipWhiteSpace();

        if (Character.isDigit(currentCharacter))
        {
            return buildNumber();
        }

        if (isValidIdentifierCharacter(currentCharacter))
        {
            return buildIdentifier();
        }

        switch (currentCharacter)
        {
            case '(': return buildToken(TokenType.LEFT_PAREN, currentCharacter);
            case ')': return buildToken(TokenType.RIGHT_PAREN, currentCharacter);
            case '[': return buildToken(TokenType.LEFT_SQUARE_BRACKET, currentCharacter);
            case ']': return buildToken(TokenType.RIGHT_SQUARE_BRACKET, currentCharacter);
            case '{': return buildToken(TokenType.LEFT_BRACE, currentCharacter);
            case '}': return buildToken(TokenType.RIGHT_BRACE, currentCharacter);
            case ';': return buildToken(TokenType.SEMICOLON, currentCharacter);
            case ':': return buildToken(TokenType.COLON, currentCharacter);
            case ',': return buildToken(TokenType.COMMA, currentCharacter);
            case '.': return buildToken(TokenType.DOT, currentCharacter);
            case '\0':return buildToken(TokenType.EOF, currentCharacter);
            case '^':return buildToken(TokenType.RAISE_TO, currentCharacter);
            case '&': return match('&') ? buildToken(TokenType.LOGICAL_AND) : buildToken(TokenType.AND, currentCharacter);
            case '|': return match('|') ? buildToken(TokenType.LOGICAL_OR) : buildToken(TokenType.OR, currentCharacter);
            case '-':
                if (match('-'))
                {
                    return buildToken(TokenType.DECREMENT);
                }
                else if(match('='))
                {
                    return buildToken(TokenType.MINUS_ASSIGN);
                }
                return buildToken(TokenType.MINUS, currentCharacter);

            case '+':
                if (match('+'))
                {
                    return buildToken(TokenType.INCREMENT);
                }
                else if(match('='))
                {
                    return buildToken(TokenType.PLUS_ASSIGN);
                }
                return buildToken(TokenType.PLUS, currentCharacter);

            case '/':  return match('=') ? buildToken(TokenType.SLASH_ASSIGN) : buildToken(TokenType.SLASH, currentCharacter);
            case '%':  return match('=') ? buildToken(TokenType.MODULO_ASSIGN) : buildToken(TokenType.MODULO, currentCharacter);
            case '*':  return match('=') ? buildToken(TokenType.STAR_ASSIGN) : buildToken(TokenType.STAR, currentCharacter);
            case '!': return match('=') ? buildToken(TokenType.NOT_EQUAL) : buildToken(TokenType.NOT, currentCharacter);
            case '=': return match('=') ? buildToken(TokenType.EQUAL_EQUAL) : buildToken(TokenType.EQUAL, currentCharacter);
            case '<': return match('=') ? buildToken(TokenType.LESS_EQUAL) : buildToken(TokenType.LESS, currentCharacter);
            case '>': return match('=') ? buildToken(TokenType.GREATER_EQUAL) : buildToken(TokenType.GREATER, currentCharacter);
            case '"': return buildString();
        }
        throw new LexicalException("Error : Unidentified character '"+currentCharacter+"' at line no "+currentLineNo);
    }

    private boolean isValidIdentifierCharacter(char ch)
    {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    private boolean isEnd()
    {
        return currentCharPos >= srcCodeSize;
    }

    public void printTokens()
    {
        Util.log(" { TokenType, Lexeme, Line No. }");
        for (Token token : tokens)
        {
            Util.log("{ "+token.getTokenType()+", "+token.getLexeme()+", "+token.getLineNo()+ " }");
        }
    }

    public boolean hasError()
    {
        return !errorMessages.isEmpty();
    }

    public void logErrorMessages()
    {
        for (String message : errorMessages)
        {
            System.out.println(message);
        }
    }
}

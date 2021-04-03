package org.teascript;

import org.teascript.ast.statement.Statement;
import org.teascript.interpreter.Interpreter;
import org.teascript.lexer.Scanner;
import org.teascript.lexer.Token;
import org.teascript.parser.PrecedenceParser;
import org.teascript.util.Util;

import java.util.List;

/**
 * Author :: Pawan Bhatt
 * Created On :: 01-01-2021
 * Purpose :: Interpreter for static type and dynamic type language.
 */
public class TeascriptMain
{
    private static final String SRC_FILE = "";

    public static void main(String[] args)
    {
        if (args.length <= 0)
        {
            String srcCode = Util.readFile(SRC_FILE);
            Scanner scanner = new Scanner(srcCode);
            List<Token> tokens = scanner.getTokens();
            scanner.printTokens();
            if (scanner.hasError())
            {
                scanner.logErrorMessages();
                System.exit(-1);
            }
            PrecedenceParser parser = new PrecedenceParser(tokens);
            List<Statement> statements = parser.parseProgram();
            if (parser.hasErrors())
            {
                parser.logErrorMessages();
                System.exit(-1);
            }

            Interpreter interpreter = new Interpreter();
            interpreter.interpret(statements);
        }
        else
        {
            run(args[0]);
        }
    }

    private static void run(String srcPath)
    {
        String srcCode = Util.readFile(srcPath);
        Scanner scanner = new Scanner(srcCode);
        List<Token> tokens = scanner.getTokens();
        if (scanner.hasError())
        {
            scanner.logErrorMessages();
            System.exit(-1);
        }
        PrecedenceParser parser = new PrecedenceParser(tokens);
        List<Statement> statements = parser.parseProgram();
        if (parser.hasErrors())
        {
            parser.logErrorMessages();
            System.exit(-1);
        }
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }
}
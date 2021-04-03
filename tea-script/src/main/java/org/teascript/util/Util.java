package org.teascript.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util
{
    public static String readFile(String filePath)
    {
        String srcCode = null;
        Path path = Paths.get(filePath);
        try
        {
            byte[] content = Files.readAllBytes(path);
            srcCode = new String(content);
        }
        catch (IOException e)
        {
            System.out.println("Error reading file path : "+filePath);
            System.exit(-1);
        }
        return srcCode;
    }

    public static void log(String message)
    {
        System.out.println(message);
    }

    public static void error(String message)
    {
        System.err.println(message);
    }
}

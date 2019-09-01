package main;

import main.lex.Lexer;
import main.lex.Token;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    private static final String fileName = "E:\\stone2\\src\\example\\test.txt";

    public static void main(String[] args) throws IOException{
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("file name is null");
        }

        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("file [ " + fileName + " ] not found");
        }
        FileInputStream fs = new FileInputStream(file);
//        int i = (int) fs.read();
//        for(; i >= 0;  i = (int) fs.read()) {
//            byte b = (byte)i;
//            System.out.println(b);
//        }

        Lexer lexer = new Lexer(fs);

        Token token = lexer.nextToken();
        for(; token.tag >= 0; token = lexer.nextToken()) {
            System.out.println(token.toString());
        }

        fs.close();
    }
}

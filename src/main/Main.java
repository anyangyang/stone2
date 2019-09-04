package main;

import main.lexer.Lexer;
import main.lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;

public class Main {

    // windows 下 lexer 测试文件
    private static final String fileName = "E:\\stone2\\src\\example\\test.txt";

    public static void main(String[] args) throws Exception{
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("file name is null");
        }

        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("file [ " + fileName + " ] not found");
        }
        FileInputStream fs = new FileInputStream(file);

        Lexer lexer = new Lexer(fs);

        Token token = lexer.nextToken();
        for(; token.tag >= 0; token = lexer.nextToken()) {
            System.out.println(token.toString());
        }

        fs.close();
    }
}

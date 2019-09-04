package main;

import main.lexer.Lexer;
import main.lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;

public class Main {

    // windows 下 lexer 测试文件
    private static final String fileName = "E:\\stone2\\src\\example\\test.txt";

    public static void main(String[] args) throws Exception{
        FileInputStream reader = initReader(fileName);
        try{
            Lexer lexer = new Lexer(reader);
            Token token = lexer.nextToken();
            for(; token.tag >= 0; token = lexer.nextToken()) {
                System.out.println(token.toString());
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }



    }

    public static FileInputStream initReader(String infile) throws IOException {
        if (StringUtils.isBlank(infile)) {
            throw new RuntimeException("file name is null");
        }

        File file = new File(infile);
        if (!file.exists()) {
            throw new RuntimeException("file [ " + infile + " ] not found");
        }

       return  new FileInputStream(file);
    }
}

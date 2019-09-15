package main;

import main.ast.StmtNode;
import main.gen.Generator;
import main.lexer.Lexer;
import main.lexer.Token;
import main.parser.Parser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class Main {

    // windows 下 lexer 测试文件
    private static final String lexTestFileName = "E:\\stone2\\src\\example\\lexTest.txt";
    // parser 测试文件
    private static final String parserTestFileName = "E:\\stone2\\src\\example\\parserTest.txt";
    // 中间代码生成测试文件
    private static final String genTestFileName = "E:\\stone2\\src\\example\\genTest.txt";
    // 中间代码生成测试文件:
    private static final String genTestFileName2 = "E:\\stone2\\src\\example\\genTest2.txt";

    public static void main(String[] args) throws Exception{
        FileInputStream reader = initReader(genTestFileName2);
        try{
            Lexer lexer = new Lexer(reader);
//            Token token = lexer.nextToken();
//            for(; token.tag >= 0; token = lexer.nextToken()) {
//                System.out.println(token.toString());
//            }
//            Parser parser = new Parser(lexer);
            List<StmtNode> stmtList = new Parser(lexer).parser();
            Generator.genCode(stmtList);

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

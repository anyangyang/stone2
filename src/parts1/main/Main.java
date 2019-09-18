package parts1.main;

import parts1.main.ast.StmtNode;
import parts1.main.gen.Generator;
import parts1.main.lexer.Lexer;
import parts1.main.parser.Parser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 *  stone2 的第一部分主要参考 《compiler》 第二版附录A
 *  在理解了附录A的源码的基础上，做了一些微小的改动
 *  ps: 附录A 代码中比较难以理解的地方，都已经做了注释
 * ps: 整个代码的流程:
 *      1、词法分析器(lexer) ，生成 token
 *      2、语法分析器(Parser)，根据 token 校验语法规则，以及生成抽象语法树森林
 *      3、中间代码生成器(Generator) ，根据语法分析器生成的抽象语法树森林生成三地址码
 *
 *      最后生成实际结果，与龙书附录A给出的结果基本一致（有一些细微的不同）
 *
 *      ps:  龙书附录A 中的语法设计还是太过简洁（生硬，不够灵活），并且也没有相应的 解释器
 *      根据抽象语法树森林进行解释执行,  所以接下来会开始 parts2，parts2 会在 parts1 的基础上，
 *      参考 《两周自制脚本语言》 这本书来实现一个更为完善的一个例子
 *
 */
public class Main {

    // windows 下 lexer 测试文件
    private static final String lexTestFileName = "E:\\stone2\\src\\parts1\\example\\lexTest.txt";
    // parser 测试文件
    private static final String parserTestFileName = "E:\\stone2\\src\\parts1\\example\\parserTest.txt";
    // 中间代码生成测试文件
    private static final String genTestFileName = "E:\\stone2\\src\\parts1\\example\\genTest.txt";
    // 中间代码生成测试文件:
    private static final String genTestFileName2 = "E:\\stone2\\src\\parts1\\example\\genTest2.txt";

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

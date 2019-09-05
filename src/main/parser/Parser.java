package main.parser;

import main.ast.IdNode;
import main.constants.CommonConst;
import main.lexer.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 语法分析的第一个目标首先是要求 token 的排列必须
 * 符合预先设计的语法规则的要求
 */
public class Parser {

    // 缓存 token
    private List<Token> tokenBuf = new ArrayList<Token>(CommonConst.BUFF_SIZE);

    private Lexer lexer;

    private Token look;
    // 符号表
    private SymbolTable curTable;
    // 变量声明的位置,
    private int offset = 0;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        getToken();
    }

    /**
     * 采用递归下降的方式进行解析
     *
     * @return
     */
    public void parser() throws IOException {
        program();
    }

    /**
     * program -->   block
     */
    public void program() throws IOException {
        block();
    }


    /**
     * block ----> { decls stmts }
     *
     * @throws IOException
     */
    public void block() throws IOException {
        match('{');
        // 创建当前作用域对应的符号表
        SymbolTable saveTable = this.curTable;
        this.curTable = new SymbolTable(saveTable);
        // 处理声明
        decls();
        // 处理语句
//        stmts();
        this.curTable = saveTable;  // 恢复符号表
        match('}');
    }

    /**
     * 处理变量声明
     * decls -----> type id   decls（非终结符）
     */
    public void decls() throws IOException {
        /**
         * 如果是变量声明,，每次解析一个变量声明
         */
        while (look.tag == Tag.BASIC.getCode()) {
            Type type = getType();
            Token word = this.look;
            match(Tag.ID.getCode());
            match(';');

            IdNode idNode = new IdNode(word, type, offset);
            curTable.put(word, idNode);
            offset = offset + type.weight;
        }

    }

    /**
     * 获取类型，在上一个方法已经验证当前 token 是一个类型
     *
     * @return
     */
    private Type getType() throws IOException {
        Type basicType = (Type) this.look;    // 基础类型 int char float bool
        getToken();
        if(this.look.tag != '[') {
           return basicType;
        }
        // 解析数组
        return getArrayType(basicType);
    }

    /**
     *  数组的定义：  basicType[num]
     * @param basicType
     * @return
     * @throws IOException
     */
    private Type getArrayType(Type basicType) throws IOException {
        getToken();
        Token num = this.look;
        /**
         *  这里会有一点小问题 ，基于之前 lex 时，real 类型
         *  的 tag 也是 num
         *  TODO:  区分实数和整数
         */
        match(Tag.NUM.getCode());
        match(']');
        // 如果是多维数组
        if(this.look.tag == '[') {
            basicType = getArrayType(basicType);
        }

        return new Array(basicType,  ((Num)num).value);
    }


    /**
     * 获取 token，如果缓存中存在未消费的token，
     * 优先从缓存中获取
     *
     * @return
     * @throws IOException
     */
    public void getToken() throws IOException {
        if (tokenBuf.size() > 0) {
            this.look = tokenBuf.remove(tokenBuf.size() - 1);     // remove
            return;
        }

        this.look = lexer.nextToken();
    }

    /**
     * 将未消费完成 token 存放到缓存中
     */
    public void unGetToken() {
        tokenBuf.add(this.look);
        this.look = null;
    }


    public void match(int t) throws IOException {
        if (this.look.tag != t) {
            error("syntax error， expect " + ((char) t));
        }

        getToken();
    }

    /**
     * 当前的错误处理是一旦遇到语法错误就返回
     *
     * @param errMsg
     */
    private void error(String errMsg) {
        throw new RuntimeException("near line " + Lexer.line + ": " + errMsg);
    }


}

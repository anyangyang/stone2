package main.parser;

import main.constants.CommonConst;
import main.lexer.Lexer;
import main.lexer.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    // 缓存 token
    private List<Token> tokenBuf = new ArrayList<Token>(CommonConst.BUFF_SIZE);

    private Lexer lexer;

    private Token look;
    // 符号表
    SymbolTable curTable;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        getToken();
    }

    /**
     * 采用递归下降的方式进行解析
     *
     * @return
     */
    public void parser() throws IOException{
        program();
    }

    /**
     * program -->   block
     */
    public void program() throws IOException{
        block();
    }


    /**
     *  block ----> { decls stmts }
     * @throws IOException
     */
    public void block() throws IOException{
        match('{');
        // 创建当前作用域对应的符号表
        SymbolTable saveTable = this.curTable;
        this.curTable = new SymbolTable(saveTable);
//        decls();
//        stmts();
        this.curTable = saveTable;  // 恢复符号表
        match('}');
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

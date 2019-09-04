package main.ast;


import main.lexer.Lexer;

public class Node {

    public int lexLine;

    public Node() {
        this.lexLine = Lexer.line;
    }

    /**
     * 展示错误信息
     *
     * @param errMsg
     */
    public void error(String errMsg) {
        throw new RuntimeException("near line " + lexLine + ": " + errMsg);
    }
}

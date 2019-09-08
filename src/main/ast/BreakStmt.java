package main.ast;

import main.lexer.Lexer;

public class BreakStmt extends StmtNode{

    public StmtNode stmt;

    public BreakStmt() {
        int size = StmtNode.Enclosing.size();
        if(size != 0 ) {
            this.stmt = StmtNode.Enclosing.get( size -1 );
        }

        if(this.stmt == null || this.stmt == StmtNode.Null) {
            error("unclose brealk");
        }
    }

}
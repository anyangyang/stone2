package main.ast;

import java.util.List;

public class IfStmt extends StmtNode {
    public ExprNode boolExpr;
    public List<StmtNode> ifBody;
    public List<StmtNode> elseBody;

    public IfStmt(ExprNode boolExpr, List<StmtNode> ifBody, List<StmtNode> elseBody) {
        this.boolExpr  = boolExpr;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }
}

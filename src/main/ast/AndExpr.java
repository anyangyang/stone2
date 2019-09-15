package main.ast;


import main.lexer.Token;

public class AndExpr extends LogicalExpr {

    public AndExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }

    public void gen(int t, int f) {
        int label = f == 0 ? newLable() : f;
        expr1.jumping(0, label);
        expr2.jumping(t, f);
        if(f == 0) {
            emitLabel(f);
        }
    }
}

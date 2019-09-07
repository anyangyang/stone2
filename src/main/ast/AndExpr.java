package main.ast;


import main.lexer.Token;

public class AndExpr extends LogicalExpr {

    public AndExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }
}

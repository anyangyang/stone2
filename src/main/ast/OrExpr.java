package main.ast;

import main.lexer.Token;

public class OrExpr extends LogicalExpr{

    public OrExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }
}

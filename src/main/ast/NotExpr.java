package main.ast;

import main.lexer.Token;

public class NotExpr extends LogicalExpr {

    public NotExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }
}

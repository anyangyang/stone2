package main.ast;

import main.lexer.Array;
import main.lexer.Token;
import main.lexer.Type;

public class RelExpr extends LogicalExpr{

    public RelExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }

    public Type check(ExprNode expr1, ExprNode expr2) {
        if(expr1.type instanceof Array || expr2.type instanceof Array) {
            return null;
        }

        if(expr1.type == expr2.type) {
            return Type.Bool;
        }

        return null;
    }
}

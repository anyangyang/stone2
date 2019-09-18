package parts1.main.ast;

import parts1.main.lexer.Array;
import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

/**
 *  逻辑表达式
 */
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


    public void jumping(int t, int f) {
        ExprNode a = expr1.reduce();
        ExprNode b = expr2.reduce();

        String test = a .toString() + " " + token.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }
}

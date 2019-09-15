package main.ast;

import main.lexer.Token;

public class NotExpr extends LogicalExpr {

    public NotExpr(Token token, ExprNode expr1) {
        /**
         * 这里是为了应对当前的类型问题做的一个妥协
         * 具体可以查看 LogicalExpr 的构造器中调用的 check()
         */
        super(token, expr1, expr1);
    }


    public void gen(int t, int f) {
        expr2.jumping(f, t);
    }
}

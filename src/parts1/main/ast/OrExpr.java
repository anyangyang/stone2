package parts1.main.ast;

import parts1.main.lexer.Token;

public class OrExpr extends LogicalExpr{

    public OrExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, expr1, expr2);
    }

    /**
     *
     * @param t
     * @param f
     */
    public void jumpint(int t, int f) {
        int label = t == 0 ? newLable() : t;
        /**
         * 如果第一个表达式为真，就跳出逻辑判断的表达式，
         * 否则就进入下一个判断
         */
        expr1.jumping(label, 0);
        // 如果第二个表达式为真那么跳转到 t，否则跳转到 f
        expr2.jumping(t, f);
        // 如果 t 为 0 话，就将 label 作为当前表达式的结果为真时的出口
        if(t == 0) {
            emitLabel(label);
        }

    }
}

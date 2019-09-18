package parts1.main.ast;

import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

/**
 *   算术表达式
 */
public class AirthExpr extends OpExpr {
    public ExprNode expr1;
    public ExprNode expr2;

    public AirthExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        super .type  = Type.max(expr1.type, expr2.type);
        if(super.type == null) {
            error("type error");
        }
    }

    /**
     * 规约子表达式，生成最终的语句
     * @return
     */
    @Override
    public ExprNode gen() {
        return new AirthExpr(token, expr1.reduce(), expr2.reduce());
    }
    // 用于打印语句
    public String toString() {
        return expr1.toString() + " " + token.toString() + " " + expr2.toString();
    }
}

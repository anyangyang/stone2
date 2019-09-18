package parts1.main.ast;

import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

/**
 * 单目运算符表达式
 */
public class UnaryExpr extends OpExpr {
    public ExprNode expr;

    public UnaryExpr(Token token, ExprNode expr) {
        super(token, null);
        this.expr = expr;
        super.type = Type.max(Type.Int, expr.type);
        if(super.type == null) {
            error("type error");
        }
    }

    @Override
    public ExprNode gen() {
        return new UnaryExpr(token, expr.reduce());
    }

    public String toString() {
        return token.toString() + " " + expr.toString();
    }
}

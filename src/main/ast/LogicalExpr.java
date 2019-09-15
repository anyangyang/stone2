package main.ast;

import main.lexer.Token;
import main.lexer.Type;

/**
 * 逻辑表达式
 */
public class LogicalExpr extends ExprNode {
    public ExprNode expr1;
    public ExprNode expr2;


    public LogicalExpr(Token token, ExprNode expr1, ExprNode expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        super.type = check(expr1, expr2);
        if (super.type == null) {
            error("type error");
        }
    }

    public Type check(ExprNode expr1, ExprNode expr2) {
        if (expr1.type == Type.Bool && expr2.type == Type.Bool) {
            return Type.Bool;
        }

        return null;
    }


    /**
     *  对表达式进行赋值
     * @return
     */
    public ExprNode gen() {
        int f = newLable();   // 如果表达式的值为 false，那么跳到给这个 t  = false
        int a = newLable();

        Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);   // 跳出赋值
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
        return temp;
    }

}

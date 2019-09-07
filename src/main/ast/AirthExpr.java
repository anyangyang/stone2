package main.ast;

import main.lexer.Token;
import main.lexer.Type;

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
}

package parts1.main.ast;

import parts1.main.lexer.Type;

/**
 *  普通变量的赋值
 */
public class SetStmt extends StmtNode{
    public IdNode id;
    public ExprNode expr;

    public SetStmt(IdNode id, ExprNode expr) {
        this.id = id;
        this.expr = expr;
        if(check(id.type, expr.type) == null) {
            error("type error");
        }
    }

    public Type check(Type type1, Type type2) {
        if(Type.numeric(type1) && Type.numeric(type2)) {
            return type2;
        } else if(type1 == Type.Bool && type2 == Type.Bool) {
            return type2;
        }

        return null;
    }

    public void gen(int begin, int after) {
        emit(id.toString() + " = " + expr.reduce().toString());
    }
}

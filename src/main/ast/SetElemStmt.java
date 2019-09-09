package main.ast;

import main.lexer.Array;
import main.lexer.Type;

public class SetElemStmt extends StmtNode {
    public IdNode array;
    public ExprNode location;   // 数组下标在数组中的连续位置
    public ExprNode expr;

    public SetElemStmt(ArrayAccess arrayAccess, ExprNode expr) {
        this.array = arrayAccess.array;
        this.location = arrayAccess.loc;
        this.expr = expr;
        // 这里需要使用数组的基本类型进行校验
        if(check(arrayAccess.type, expr.type) == null) {
            error("type error");
        }
    }

    public Type check(Type type1, Type type2) {
        // ArrayAccess 对象解析出来的时候， ArrayAccess 所属的 Type 应该为 basicType
       if(type1 instanceof Array || type2 instanceof Array) {
            return null;
       }
       else if (type1 == type2) {
            return type2;
       }
       else if(Type.numeric(type1) && Type.numeric(type2)) {
            return type2;
        }

        return null;
    }

    public void gen(int begin, int after) {
        ExprNode locationTemp = location.reduce();
        ExprNode expr1 = expr.reduce();
        emit(array.toString() + " [ " + locationTemp.toString() + " ] " + " = " + expr1.toString());
    }


}

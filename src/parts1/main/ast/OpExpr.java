package parts1.main.ast;

import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

/**
 *  用于给 算数表达式提供一些公共的方法
 */
public class OpExpr extends ExprNode{

    public OpExpr(Token token , Type type) {
        super(token, type);
    }

    /**
     *  ps:  ConstNode 并没有继承 OpExpr 类，
     *  这样可以杜绝无限递归返回自身， 导致栈溢出
     *  ps：ConstNode 对象的 reduce 使用 父类 ExprNode 的reduce，返回自身
     *
     * @return
     */
    public ExprNode reduce() {
        // 如果当前表达式树依旧存在子表达式树，继续规约
        ExprNode x  = gen();
        // 生成临时变量
        Temp temp = new Temp(this.type);
        // 打印规约的语句
        emit(temp.toString() + " = " + x.toString());
        return temp;
    }
}

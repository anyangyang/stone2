package main.ast;

import java.util.List;

public class WhileStmt extends StmtNode {
    public ExprNode expr;  // 循环的条件
    public List<StmtNode> stmtBody;

    /**
     *  这里增加 init 方法进行初始化，而不在构造器中
     *  进行初始化的原因是为了处理 break 语句，具体的情况请看语句分析
     * @param expr
     * @param stmtBody
     */
    public void init(ExprNode expr, List<StmtNode> stmtBody) {
        this.expr = expr;
        this.stmtBody = stmtBody;
    }

    public void gen(int b, int a) {
        // 记录 do 语句的出口
        this.after = a;
        expr.jumping(0, a);
        int stmtLable = newLable();
        emitLabel(stmtLable);
        bodyGen(stmtBody, stmtLable, a);
        emit("goto L" + b);   // 跳回到语句开始的地方，重新进行条件表达式的判断
    }

}

package main.ast;

import java.util.List;

public class DoStmt extends StmtNode {
    public List<StmtNode> stmtBody;
    public ExprNode expr;  // 循环的条件

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
        after = a;
        int exprLabel = newLable();   // 测试表达式的开始标号

        // 生成语句
        bodyGen(stmtBody, b, exprLabel);
        emitLabel(exprLabel);
        expr.jumping(b, 0);
    }
}

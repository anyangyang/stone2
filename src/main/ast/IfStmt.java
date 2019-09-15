package main.ast;

import main.gen.Generator;
import main.util.ListUtil;

import java.util.List;

public class IfStmt extends StmtNode {
    public ExprNode boolExpr;
    public List<StmtNode> ifBody;
    public List<StmtNode> elseBody;

    public IfStmt(ExprNode boolExpr, List<StmtNode> ifBody, List<StmtNode> elseBody) {
        this.boolExpr = boolExpr;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    /**
     * @param b
     * @param a
     */
    public void gen(int b, int a) {
        if (ListUtil.isNotBlank(ifBody)) {
            // if 语句
            if (ListUtil.isBlank(elseBody)) {
                ifGen(b, a);
            }
            // if - else 语句
            else {
                ifElseGen(b, a);
            }
        }
    }

    public void ifGen(int b, int a) {
        /**
         *  如果 expr 的结果为 true 继续往下执行到语句，
         *  如果为  false ，跳到外部语句结束的地方
         */

        boolExpr.jumping(0, a);
        int stmtLabel = newLable();   //  if 语句的 label
        emitLabel(stmtLabel);
        this.bodyGen(ifBody, stmtLabel, a);
    }


    public void ifElseGen(int b, int a) {
        int label1 = newLable();    // if body 的标号
        int label2 = newLable();   // else body  的标号
        boolExpr.jumping(0, label2);
        // 生成 if body 的语句
        emitLabel(label1);
        this.bodyGen(ifBody, label1, a);
        emit("goto L" + a);    // 跳过 else 语句

        // 生成 else 语句
        emitLabel(label2);
        this.bodyGen(ifBody, label2, a);
    }
}

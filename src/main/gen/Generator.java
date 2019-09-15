package main.gen;

import main.ast.Node;
import main.ast.StmtNode;
import main.util.ListUtil;

import java.util.List;

/**
 * 三地址码生成器
 */
public class Generator {

    public static void genCode(List<StmtNode> stmtList) {

        // 语句开始标号
        Integer begin = Node.newLable();
        // 语句结束标号
        Integer after = Node.newLable();
        // 打印开始标号
        Node.emitLabel(begin);
        // 生成语句中间代码
        genStmt(stmtList, begin, after);
        // 打印结束标号
        Node.emitLabel(after);
    }

    public static void genStmt(List<StmtNode> stmtList, int b, int a) {
        if (ListUtil.isBlank(stmtList)) {
            return;
        }
        // 生成语句中间代码
        int prevBeginLabel;
        //  这样赋值的原因是当前语句的开始标签是上一条语句的结束标签
        int prevAfterLabel = b;
        int size = stmtList.size();
        for (int i= 0; i < size; i++) {
            StmtNode stmt = stmtList.get(i);
            if (stmt == StmtNode.Null) {
                continue;
            }

            // 获取当前语句的开始标号
            prevBeginLabel = prevAfterLabel;
            // 获取当前语句的结束标号
            prevAfterLabel = (i == (size -1)) ? a : Node.newLable();
            // 生成语句
            stmt.gen(prevBeginLabel, prevAfterLabel);
            // 打印当前语句的结束标号
            if(prevAfterLabel != a) {
                Node.emitLabel(prevAfterLabel);
            }

        }
    }
}

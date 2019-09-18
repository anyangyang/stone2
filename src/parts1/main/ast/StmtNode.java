package parts1.main.ast;

import parts1.main.gen.Generator;

import java.util.ArrayList;
import java.util.List;

public class StmtNode extends Node {

    // 空语句，用于
    public static StmtNode Null = new StmtNode();

    /**
     *  用于 break 语句，break 语句需要跳出循环
     *  用于保存当前 break 语句所处的 while 循环内部
     */

    public static List<StmtNode> Enclosing = new ArrayList();

    /**
     *  在 do 语句和 while 语句中，保存这些语句的出口
     *  以便在 break 语句中使用
     */
    public int after = 0;

    // 用于语句生成中间代码
    public void gen(int begin, int after) {}


    public void bodyGen(List<StmtNode> stmtList, int b, int a) {
        Generator.genStmt(stmtList, b, a);
    }
}

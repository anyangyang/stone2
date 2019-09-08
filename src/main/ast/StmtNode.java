package main.ast;

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
}

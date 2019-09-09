package main.gen;

import main.ast.Node;
import main.ast.StmtNode;
import main.util.ListUtil;

import java.util.List;

/**
 * 三地址码生成器
 */
public class Generator {

//    public List<StmtNode> stmtList;

//    public Generator(List<StmtNode> stmtList) {
//        this.stmtList = stmtList;
//    }

    public static void genCode(List<StmtNode> stmtList) {
        if(ListUtil.isBlank(stmtList)){
            System.out.println("未获取到有效的语句");
            return;
        }

        // 语句开始标号
        Integer begin = Node.newLable();
        // 语句结束标号
        Integer after = Node.newLable();

        Node.emitLabel(begin);

        for(StmtNode stmt : stmtList) {
            stmt.gen(begin, after);
        }

        Node.emitLabel(after);
    }
}

package parts1.main.ast;


import parts1.main.lexer.Lexer;

public class Node {

    public  int lexLine;
    // 用于生成标号
    public static  int labels = 0;

    public Node() {
        this.lexLine = Lexer.line;
    }

    /**
     * 获取新的标号
     *
     * @return
     */
    public static  int newLable() {
        return ++labels;
    }

    /**
     * 打印标号， 用于跳转
     *
     * @param label
     */
    public static void emitLabel(int label) {
        System.out.println("L" + label + ":");
    }

    /**
     * 输入转换换成的三地址码
     *
     * @param s
     */
    public static void emit(String s) {
        System.out.println("\t" + s);    // tab
    }

    /**
     * 展示错误信息
     *
     * @param errMsg
     */
    public  void error(String errMsg) {
        throw new RuntimeException("near line " + lexLine + ": " + errMsg);
    }
}

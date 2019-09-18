package parts1.main.ast;

import parts1.main.lexer.KeyWord;
import parts1.main.lexer.Type;

/**
 *  用于生成临时变量
 */
public class Temp extends ExprNode{
    // 用于生成临时变量的标号
    public static int count = 0;
    // 当前临时变量的标号
    public int number = ++count;

    public Temp(Type type) {
        super(KeyWord.temp, type);
    }

    public String toString() {
        return "t" + number;
    }
}

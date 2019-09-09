package main.ast;

import main.lexer.KeyWord;
import main.lexer.Type;

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

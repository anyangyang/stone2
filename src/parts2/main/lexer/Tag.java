package parts2.main.lexer;

public class Tag {

    public final static int
        // 逻辑运算符标记
        AND = 256, OR = 257, NOT = 258,
        // 语句保留关键字
        IF = 259, ELSE = 260, DO = 261, WHILE = 262, BREAK = 263,
        // 比较运算符
        EQ = 264, NE = 265, GE = 266, LE = 267,
        // 布尔值
        TRUE = 268, FALSE = 269,
        // 变量，整数，实数, 负数
        ID = 270, NUM = 271, REAL = 272, MINUS = 273;

}

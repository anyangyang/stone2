package parts2.main.lexer;

public class Token {
    public int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    /**
     *  文件结束符
     */
    public static Token EOF = new Token(-1);

    public String toString() {
        return "" + tag;
    }
}

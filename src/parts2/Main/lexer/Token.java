package parts2.Main.lexer;

public class Token {
    public int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    /**
     *  文件结束符
     */
    public static Token EOF = new Token(-1);
}

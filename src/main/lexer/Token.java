package main.lexer;

public class Token {
    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    public String toString() {
        return "" +(char) tag;
    }

    public static Token EOF = new Token(-1);
}

package main.lex;

public class Token {
    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    public String toString() {
        return "" + tag;
    }
}

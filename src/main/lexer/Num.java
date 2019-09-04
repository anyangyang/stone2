package main.lexer;

public class Num extends Token {
    public final int value;

    public Num(int value) {
        super(Tag.NUM.getCode());
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}

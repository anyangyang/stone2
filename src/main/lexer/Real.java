package main.lexer;

public class Real extends Token {
    public final float value;
    public Real(float value) {
        super(Tag.REAL.getCode());
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}

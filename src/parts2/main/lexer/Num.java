package parts2.main.lexer;

/**
 * 整数
 */
public class Num extends Token {

    public int value;

    public Num(int value, int tag) {
        super(tag);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}

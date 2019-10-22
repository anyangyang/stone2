package parts2.main.lexer;

/**
 * 实数
 */
public class Real extends Token {

    public float value;

    public Real(float value, int tag) {
        super(tag);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}

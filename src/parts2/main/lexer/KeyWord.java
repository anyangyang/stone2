package parts2.main.lexer;

public class KeyWord extends Token {

    public String value;

    public final static KeyWord
            and = new KeyWord("&&", Tag.AND),
            or = new KeyWord("||", Tag.OR),
            eq = new KeyWord("==", Tag.EQ),
            ne = new KeyWord("!=", Tag.NE),
            ge = new KeyWord(">=", Tag.GE),
            le = new KeyWord("<=", Tag.LE),
            True = new KeyWord("true", Tag.TRUE),
            False = new KeyWord("false", Tag.FALSE);


    public KeyWord(String value, int tag) {
        super(tag);
        this.value = value;
    }

    public String toString() {
        return value;
    }
}

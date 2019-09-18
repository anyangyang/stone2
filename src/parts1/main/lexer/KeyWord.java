package parts1.main.lexer;

public class KeyWord extends Token{

    public String lexme;
    public KeyWord(String lexme, int tag) {
        super(tag);
        this.lexme = lexme;
    }


    public static final KeyWord
    and = new KeyWord("&&", Tag.AND),
    or = new KeyWord("||", Tag.OR),
    eq = new KeyWord("==", Tag.EQ),
    ne = new KeyWord("!=", Tag.NE),
    le = new KeyWord("<=", Tag.LE),
    ge = new KeyWord(">=", Tag.GE),
    minus = new KeyWord("minus", Tag.MINUS),
    True = new KeyWord("true", Tag.TRUE),
    False = new KeyWord("false", Tag.FALSE),
    temp = new KeyWord("t", Tag.TEMP);

    public String toString() {
        return lexme;
    }
}

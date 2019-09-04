package main.lexer;

public class KeyWord extends Token{

    public String lexme;
    public KeyWord(String lexme, int tag) {
        super(tag);
        this.lexme = lexme;
    }


    public static final KeyWord
    and = new KeyWord("&&", Tag.AND.getCode()),
    or = new KeyWord("||", Tag.OR.getCode()),
    eq = new KeyWord("==", Tag.EQ.getCode()),
    ne = new KeyWord("!=", Tag.NE.getCode()),
    le = new KeyWord("<=", Tag.LE.getCode()),
    ge = new KeyWord(">=", Tag.GE.getCode()),
    minus = new KeyWord("minus", Tag.MINUS.getCode()),
    True = new KeyWord("true", Tag.TRUE.getCode()),
    False = new KeyWord("false", Tag.FALSE.getCode()),
    temp = new KeyWord("t", Tag.FALSE.getCode());

    public String toString() {
        return lexme;
    }
}

package parts1.main.lexer;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 词法解析器
 */
public class Lexer extends BufferReader {

    private Map<String, KeyWord> words = new HashMap();

    private BigDecimal ten = new BigDecimal(10);

    public Lexer(String infile) throws IOException{
        super(infile);
        reserve();
    }

    public Lexer(FileInputStream reader) throws Exception {
        super(reader);
        reserve();
    }

    public void reserve() {
        // 保留字
        reserve(new KeyWord("if", Tag.IF));
        reserve(new KeyWord("else", Tag.ELSE));
        reserve(new KeyWord("while", Tag.WHILE));
        reserve(new KeyWord("do", Tag.DO));
        reserve(new KeyWord("break", Tag.BREAK));
        reserve(KeyWord.True);
        reserve(KeyWord.False);
        reserve(Type.Int);
        reserve(Type.Char);
        reserve(Type.Float);
        reserve(Type.Bool);
    }

    public void reserve(KeyWord word) {
        words.put(word.lexme, word);
    }


    public Token nextToken() throws IOException {
        skipWhiteSpace();
        switch (this.peek) {
            case '&':
                return next('&') ? KeyWord.and : new Token('&');
            case '|':
                return next('|') ? KeyWord.or : new Token('|');
            case '=':
                return next('=') ? KeyWord.eq : new Token('=');
            case '!':
                return next('=') ? KeyWord.ne : new Token('!');
            case '<':
                return next('=') ? KeyWord.le : new Token('<');
            case '>':
                return next('=') ? KeyWord.ge : new Token('>');
            case -1:
                return Token.EOF;
            default:
                // 数字
                if (isDigit()) {
                    return readNum();
                }
                // 变量
                if (isLetter()) {
                    return readId();
                }
        }

        Token token = new Token(this.peek);
        return token;
    }

    private boolean isDigit() {
        return Character.isDigit((char) this.peek);
    }

    private Token readNum() throws IOException {
        int v = 0;
        do {
            v = v * 10 + Character.digit(this.peek, 10);
            getChar();
        } while (this.isDigit());

        if (this.peek != '.') {
            ungetCh();
            return new Num(v);
        }
        BigDecimal f = new BigDecimal(v);
        BigDecimal d = ten;
        getChar();
        for (; ; getChar()) {
            if (!isDigit()) {
                ungetCh();
                break;
            }
            char c = (char) this.peek;
            BigDecimal t = new BigDecimal(Character.digit(c, 10));
            f = f.add(t.divide(d));
            d = d.multiply(ten);
        }
        return new Real(f.floatValue());
    }

    private Token readId() throws IOException {
        StringBuilder sb = new StringBuilder();
        do {
            char ch = (char) this.peek;
            sb.append(ch);
            getChar();
        } while (isLetterOrDigit());

        ungetCh();
        String s = sb.toString();
        KeyWord word = words.get(s);
        if (word != null) {
            return word;
        }

        KeyWord id = new KeyWord(s, Tag.ID);
        words.put(id.lexme, id);   // 在符号表中会使用
        return id;
    }

    private boolean isLetterOrDigit() {
        return Character.isLetterOrDigit(this.peek);
    }

    private boolean isLetter() {
        return Character.isLetter(this.peek);
    }

    /**
     * 跳过空格
     */
    private void skipWhiteSpace() throws IOException {
        getChar();
        for (; ; getChar()) {
            // TODO 跳过行注释和块注释

            if (this.peek == ' ' || this.peek == '\t' || this.peek == '\r') {
                continue;
            } else if (this.peek == '\n' ) {
                line++;
            } else {
                break;
            }
        }
    }
}

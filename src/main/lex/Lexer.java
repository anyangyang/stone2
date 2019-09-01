package main.lex;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Lexer {
    public static int line = 1;
    private FileInputStream reader;
    private int peek; // 用于前看一个字符
    private Map<String, KeyWord> words = new HashMap();

    private BigDecimal ten = new BigDecimal(10);
    public Lexer(FileInputStream reader) throws IOException {
        this.reader = reader;
        // 保留字
        reserve(new KeyWord("if", Tag.IF.getCode()));
        reserve(new KeyWord("else", Tag.ELSE.getCode()));
        reserve(new KeyWord("while", Tag.WHILE.getCode()));
        reserve(new KeyWord("do", Tag.DO.getCode()));
        reserve(new KeyWord("break", Tag.BREAK.getCode()));
        reserve(KeyWord.True);
        reserve(KeyWord.False);
        reserve(KeyWord.True);
        reserve(KeyWord.True);

    }

    public void reserve(KeyWord word) {
        words.put(word.lexme, word);
    }

    /**
     * 从输入流中读取一个字符
     */
    public void readch() throws IOException {
        this.peek =  (int)reader.read();
    }

    boolean readch(int c) throws IOException {
        this.readch();
        if (this.peek != c) {
            return false;
        }
        this.peek = -1;
        return true;
    }

    public Token nextToken() throws IOException {
        skipWhiteSpace();
        switch (this.peek) {
            case '&':
                return readch('&') ? KeyWord.and : new Token('&');
            case '|':
                return readch('|') ? KeyWord.or : new Token('|');
            case '=':
                return readch('=') ? KeyWord.eq : new Token('=');
            case '!':
                return readch('=') ? KeyWord.ne : new Token('!');
            case '<':
                return readch('=') ? KeyWord.le : new Token('<');
            case '>':
                return readch('=') ? KeyWord.ge : new Token('>');
            default:
                if (isDigit()) {
                    return readNum();
                }

                if (isLetter()) {
                    return readId();
                }
        }

        Token token = new Token(this.peek);
        return token;
    }

    private boolean isDigit() {
        return Character.isDigit((char)this.peek);
    }

    private Token readNum() throws IOException {
        int v = 0;
        do {
            v = v * 10 + Character.digit(this.peek, 10);
            this.readch();
        } while (this.isDigit());

        if (this.peek != '.') {
            return new Num(v);
        }
        BigDecimal f = new BigDecimal(v);
        BigDecimal d = ten;
        readch();
        for (; ; readch()) {
            if (!isDigit()) {
                break;
            }
            char c = (char)this.peek;
            BigDecimal t = new BigDecimal(Character.digit(c, 10));
            f = f.add(t.divide(d));
            d = d.multiply(ten);
        }
        return new Real(f.floatValue());
    }

    private Token readId() throws IOException {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(this.peek);
            readch();
        } while (isLetterOrDigit());

        String s = sb.toString();
        KeyWord word = words.get(s);
        if(word != null) {
            return word;
        }

        KeyWord id = new KeyWord(s, Tag.ID.getCode());
        words.put(id.lexme, id);
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
        readch();
        for (; ; readch()) {
            if (this.peek == ' ' || this.peek == '\t') {
                continue;
            } else if (this.peek == '\n') {
                this.line++;
            } else {
                break;
            }
        }
    }


}

package parts2.main.lexer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Lexer extends ReadBuffer {

    public final static BigDecimal TEN = new BigDecimal(10);

    public final static Map<String, Token> map = new HashMap<String, Token>();



    public Lexer(FileInputStream reader) {
        super(reader);
        this.reserve();
    }

    public void reserve() {
        this.reserve(new KeyWord("do", Tag.DO));
        this.reserve(new KeyWord("while", Tag.WHILE));
        this.reserve(new KeyWord("if", Tag.IF));
        this.reserve(new KeyWord("else", Tag.ELSE));
        this.reserve(new KeyWord("break", Tag.BREAK));
        this.reserve(KeyWord.True);
        this.reserve(KeyWord.False);
    }

    public void reserve(KeyWord keyWord) {
        map.put(keyWord.value, keyWord);
    }

    public Token nextToken() throws IOException {
        // 跳过空格和注释
        this.skipSpaceAndComment();
        // 获取一个字符
        this.getChar();
        // 根据字符生成 Token,
        switch (this.peek) {
            case '=':
                return expect('=') ? KeyWord.eq : new Token('=');
            case '!':
                return expect('=') ? KeyWord.ne : new Token('!');
            case '<':
                return expect('=') ? KeyWord.le : new Token('<');
            case '>':
                return expect('=') ? KeyWord.ge : new Token('>');
            case '&':
                return expect('&') ? KeyWord.and : new Token('&');
            case '|':
                return expect('|') ? KeyWord.and : new Token('|');
            default:
                if (Character.isDigit(this.peek)) {
                    return readDigit();
                } else if (Character.isLetter(this.peek)) {
                    return readId();
                }

                return new Token(this.peek);
        }
    }

    private Token readId() throws IOException {
        StringBuffer sb = new StringBuffer(this.peek);
        getChar();
        while(Character.isLetterOrDigit(this.peek)) {
            sb.append(this.peek);
        }

        unGetChar();
        String value = sb.toString();
        if(map.containsKey(value)) {
            return map.get(value);
        }

        KeyWord id = new KeyWord(value, Tag.ID);
        map.put(value, id);

        return id;
    }

    private Token readDigit() throws IOException {
        // 先处理整数
        int v = 0;
        do {
            v = v * 10 + Character.digit(this.peek, 10);
            this.getChar();
        } while (Character.isDigit(this.peek));

        if (this.peek != '.') {
            unGetChar();
            return new Num(v, Tag.NUM);
        }

        // 再处理小数
        this.getChar();
        BigDecimal f = new BigDecimal(v);
        BigDecimal b = TEN;

        while (true) {
            if (!Character.isDigit(this.peek)) {
                unGetChar();
                break;
            }

            BigDecimal n = new BigDecimal(Character.digit(this.peek, 10));
            n = n.divide(b);
            f = f.add(n);

            b = b.multiply(TEN);
            getChar();
        }

        return new Real(f.floatValue(), Tag.REAL);
    }

    /**
     * 跳过无用的字符和注释
     * 无用的字符包括： ' ' , \t \n  \r 等
     * 注释包括行注释和块注释
     */
    private void skipSpaceAndComment() throws IOException {
        // 先读取一个字符
        this.getChar();
        //  进入循环判断
        for (; ; this.getChar()) {
            // 如果是注释
            if (this.peek == '/') {
                //  行注释
                if (expect('/')) {
                    this.skipLineComment();
                }
                //  块注释
                else if (expect('*')) {
                    this.skipBlockComment();
                }
                // 报告一个错误
                else {
                    error("skip commend error, expect / or *");
                }
            }
            // 跳过特殊字符
            else if (this.peek == ' ' || this.peek == '\t' || this.peek == '\r') {
                continue;
            } else if (this.peek == '\n') {
                this.lineNum++;
            } else {
                break;
            }
        }
    }

    /**
     * 跳过行注释
     * ps: 考虑如果是 EOF
     */
    private void skipLineComment() throws IOException {
        this.getChar();
        while (this.peek != '\n') {
            // 如果是文件结束符
            if (this.peek == EOF) {
                unGetChar();
                return;
            }
            this.getChar();
        }
        // 消费了换行符
        this.lineNum++;
    }

    /**
     * 跳过块注释
     */
    private void skipBlockComment() throws IOException {
        this.getChar();
        boolean sign = false;     // 如果当前字符是 * 将该标记设置为 true
        while (this.peek != '/' || !sign) {
            if (this.peek == EOF) {
                error("block comment parse error, expect */ end");
            } else if (this.peek == '\n') {
                this.lineNum++;
            }

            sign = this.peek == '*';
            this.getChar();
        }
    }
}

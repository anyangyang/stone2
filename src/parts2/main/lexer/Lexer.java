package parts2.main.lexer;

import java.io.Reader;

public class Lexer extends ReadBuffer{

    public Lexer(Reader reader) {
        super(reader);
    }

    public Token nextToken() {
        // 跳过空格和注释
        this.skipSpaceAndComment();
        // 获取一个字符

        // 根据字符生成 Token

        return null;
    }

    private void skipSpaceAndComment() {

    }
}

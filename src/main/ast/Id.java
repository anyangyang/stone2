package main.ast;

import main.lexer.Token;
import main.lexer.Type;

public class Id extends Expr {
    public int offset;  // 相对地址
    public Id(Token token, Type type, int offset) {
        super(token, type);
        this.offset = offset;
    }
}

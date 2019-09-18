package parts1.main.ast;

import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

public class IdNode extends ExprNode {
    public int offset;  // 相对地址
    public IdNode(Token token, Type type, int offset) {
        super(token, type);
        this.offset = offset;
    }
}

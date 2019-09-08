package main.ast;

import main.lexer.KeyWord;
import main.lexer.Tag;
import main.lexer.Token;
import main.lexer.Type;

public class ArrayAccess extends OpExpr{
    public IdNode array;
    public ExprNode loc;

    public ArrayAccess(IdNode id, ExprNode location, Type type) {
        super(new KeyWord("[]", Tag.INDEX), type);
        this.array = id;
        this.loc = location;
    }
}

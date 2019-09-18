package parts1.main.ast;

import parts1.main.lexer.KeyWord;
import parts1.main.lexer.Tag;
import parts1.main.lexer.Type;

public class ArrayAccess extends OpExpr {
    public IdNode array;
    public ExprNode loc;

    public ArrayAccess(IdNode id, ExprNode location, Type type) {
        super(new KeyWord("[]", Tag.INDEX), type);
        this.array = id;
        this.loc = location;
    }

    @Override
    public ExprNode gen() {
        return new ArrayAccess(array, loc.reduce(), type);
    }

    public String toString() {
        return array.toString() + " [ " + loc.toString() + " ] ";
    }
}

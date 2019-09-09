package main.ast;

import main.lexer.KeyWord;
import main.lexer.Num;
import main.lexer.Token;
import main.lexer.Type;

public class ConstNode extends ExprNode {

    public ConstNode(Token token, Type type) {
        super(token, type);
    }

    // 用于数组访问时根据数组下标，计算实际的位置
    public ConstNode(int i) {
        super(new Num(i), Type.Int);
    }

    public static final ConstNode
            True = new ConstNode(KeyWord.True, Type.Bool),
            False = new ConstNode(KeyWord.False, Type.Bool);


}

package parts1.main.ast;

import parts1.main.lexer.KeyWord;
import parts1.main.lexer.Num;
import parts1.main.lexer.Token;
import parts1.main.lexer.Type;

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


    public void jumping(int t, int f) {
        if(this == True && t != 0) {
            emit("goto L" + t);
        }

        else if (this == False && f != 0) {
            emit("goto L" + f);
        }
    }
}

package main.ast;

import main.lexer.Token;
import main.lexer.Type;

public class ExprNode extends Node {
    // 大部分时候，token 是一个操作符
    public Token token;
    public Type type;

    public ExprNode(Token token, Type type) {
        this.token = token;
        this.type = type;
    }

    /**
     * gen 函数生成返回一个项，这个项是三地址码的右部
     * 举个简单的例子：
     * a = a + 1;
     * 其中 a + 1 会被 reduce 函数规约成 以下形式
     *          t1 = a + 1
     * 然后就得到一个新的赋值语句:  a = t1
     * <p>
     * ps: 当前 expr 节点是一个 NUM、ID，TRUE，FALSE，需要返回自身
     *
     * @return
     */
    public ExprNode gen() {
        return this;
    }

    /**
     * 规约函数，规约当前表达式树的子树
     * 具体的规则参考 gen 函数的注释
     *
     * @return
     */
    public ExprNode reduce() {
        return this;
    }

    public String toString() {
        return token.toString();
    }
}

package main.ast;

import main.lexer.Token;
import main.lexer.Type;

/**
 *  用于给 算数表达式提供一些公共的方法
 */
public class OpExpr extends ExprNode{

    public OpExpr(Token token , Type type) {
        super(token, type);
    }
}

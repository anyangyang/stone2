package main.ast;

import main.lexer.Token;
import main.lexer.Type;

public class ExprNode extends Node{
    public Token token;
    public Type type;

    public ExprNode(Token token, Type type) {
        this.token = token;
        this.type = type;
    }
}

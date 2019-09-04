package main.ast;

import main.lexer.Token;
import main.lexer.Type;

public class Expr extends Node{
    public Token token;
    public Type type;

    public Expr(Token token, Type type) {
        this.token = token;
        this.type = type;
    }
}

package main.parser;

import main.ast.IdNode;
import main.lexer.Token;

import java.util.HashMap;

/**
 *  简单的符号表
 */
public class SymbolTable {
    public HashMap map;
    public SymbolTable prev;   // 外层的符号表

    public SymbolTable(SymbolTable prev) {
        this.map = new HashMap();
        this.prev = prev;
    }

    public void put(Token token, IdNode idNode) {
        map.put(token, idNode);
    }

    public IdNode get(Token token) {
        for(SymbolTable table = this; table != null; table = table.prev) {
            IdNode found = table.get(token);
            if(found != null) {
                return found;
            }
        }

        return null;
    }

}

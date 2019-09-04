package main.parser;

import main.ast.Id;
import main.lexer.Token;

import java.util.HashMap;
import java.util.Hashtable;

/**
 *  简单的符号表
 */
public class SymbolTable {
    public HashMap map;
    public SymbolTable prev;   // 外层的符号表

    public SymbolTable(SymbolTable prev) {
            this.prev = prev;
    }

    public void put(Token token, Id id) {
        map.put(token, id);
    }

    public Id get(Token token) {
        for(SymbolTable table = this; table != null; table = table.prev) {
            Id found = table.get(token);
            if(found != null) {
                return found;
            }
        }

        return null;
    }

}

package main.lexer;

public class Array extends Type {

    public Type of;    // 数组类型
    public int size;     // 数组个数，默认为 1

    public Array(Type of, int size) {
        super("[]", Tag.INDEX, of.weight * size);
        this.of = of;
        this.size = size;
    }

    public String toString() {
        return of.lexme + "[" + size + "]";
    }
}

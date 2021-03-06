package parts1.main.lexer;

public class Array extends Type {

    public Type of;    // 数组类型
    public int size;     // 数组个数，默认为 1

    public Array(Type of, Integer size) {
        super("[]", Tag.INDEX, of.weight * size);
        this.of = of;
        this.size = size == null ? 1 : size;
    }

    public String toString() {
        return of.lexme + "[" + size + "]";
    }
}

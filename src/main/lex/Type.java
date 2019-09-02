package main.lex;

public class Type extends KeyWord{
    public int weight;  // 用于分配长度
    public Type(String lexme, int tag, int weight) {
        super(lexme, tag);
        this.weight = weight;
    }

    public static final Type
        Int = new Type("int", Tag.BASIC.getCode(), 4),
        Char = new Type("char", Tag.BASIC.getCode(), 1),
        Bool = new Type("bool", Tag.BASIC.getCode(), 1),
        Float = new Type("float", Tag.BASIC.getCode(), 8);

    /**
     *  判断是否能够进行类型转换
     * @param t
     * @return
     */
    public static boolean numeric(Type t) {
        if( t == Type.Int || t == Type.Char || t == Type.Float) {
            return true;
        }

        return false;
    }

    public static Type max(Type t1, Type t2) {
        if(!numeric(t1) || !numeric(t2)) {
            return null;
        }

        if(t1 == Type.Float || t2 == Type.Float) {
            return Type.Float;
        }

        if(t1 == Type.Int || t2 == Type.Int ){
            return Type.Int;
        }

        return Type.Char;
    }
}

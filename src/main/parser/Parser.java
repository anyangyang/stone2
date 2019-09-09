package main.parser;

import main.ast.*;
import main.constants.CommonConst;
import main.lexer.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 语法分析的第一个目标首先是要求 token 的排列必须
 * 符合预先设计的语法规则的要求
 */
public class Parser {

    // 缓存 token
    private List<Token> tokenBuf = new ArrayList<Token>(CommonConst.BUFF_SIZE);

    private Lexer lexer;

    private Token look;
    // 符号表
    private SymbolTable curTable;
    // 变量声明的位置,
    private int offset = 0;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        move();
    }

    /**
     * 采用递归下降的方式进行解析
     *
     * @return
     */
    public List<StmtNode> parser() throws IOException {
        return program();
    }

    /**
     * program -->   block
     */
    public  List<StmtNode> program() throws IOException {
        return block();
    }


    /**
     * block ----> { decls stmts }
     *
     * @throws IOException
     */
    public List<StmtNode> block() throws IOException {
        match('{');
        // 创建当前作用域对应的符号表
        SymbolTable saveTable = this.curTable;
        this.curTable = new SymbolTable(saveTable);
        // 处理声明
        decls();
        // 处理语句
        List<StmtNode> stmtList = stmts();
//        List<StmtNode> stmtList = null;
        this.curTable = saveTable;  // 恢复符号表
        match('}');

        return stmtList;
    }

    /**
     * 解析语句
     */
    private List<StmtNode> stmts() throws IOException {
        List<StmtNode> stmtList = new ArrayList<StmtNode>();
        // 如果是结束符号
        if (this.look.tag == '}') {
            stmtList.add(StmtNode.Null);     // 其实空语句可以不用加
            return stmtList;
        }

        // 开始循环解析
        while (this.look.tag != '}') {
            stmt(stmtList);
        }

        return stmtList;
    }

    /**
     * 解析语句
     *
     * @return
     */
    private void stmt(List<StmtNode> stmtList) throws IOException {
        switch (this.look.tag) {
            // 空语句
            case ';':
                stmtList.add(StmtNode.Null);
                move();
                break;
            //if语句
            case Tag.IF:
                stmtList.add(ifStmt());
                break;
            // do while 语句
            case Tag.DO:
                stmtList.add(doStmt());
                break;
            // while 语句
            case Tag.WHILE:
                stmtList.add(whileStmt());
                break;
            // 块
            case '{':
                stmtList.addAll(block());
                break;
            // break 语句
            case Tag.BREAK:
                stmtList.add(new BreakStmt());
                break;
            // 赋值
            default:
                stmtList.add(assignStmt());
                break;
        }
    }

    private StmtNode doStmt() throws IOException {
        List<StmtNode> stmtList = new ArrayList<StmtNode>();
        DoStmt doStmt= new DoStmt();
        StmtNode.Enclosing.add(doStmt);     // 为了处理 break 语句
         match(Tag.DO);
         stmt(stmtList);
         match(Tag.WHILE);
         match('(');
         ExprNode expr = bool();
         match(')');
         match(';');
         doStmt.init(expr, stmtList);
         // 退出当前循环
        StmtNode.Enclosing.remove(StmtNode.Enclosing.size() - 1);

        return doStmt;
    }

    private StmtNode whileStmt() throws IOException {
        List<StmtNode> stmtList = new ArrayList<StmtNode>();
        WhileStmt whileStmt= new WhileStmt();
        StmtNode.Enclosing.add(whileStmt);     // 为了处理 break 语句
        match(Tag.WHILE);
        match('(');
        ExprNode expr = bool();
        match(')');
        stmt(stmtList);
        whileStmt.init(expr, stmtList);
        // 退出当前循环
        StmtNode.Enclosing.remove(StmtNode.Enclosing.size() - 1);
        return new StmtNode();
    }

    /**
     * 处理赋值语句
     *
     * @return
     * @throws IOException
     */
    private StmtNode assignStmt() throws IOException {
        Token token = this.look;
        match(Tag.ID);
        IdNode id = curTable.get(token);
        if(id == null) {
            error("id [ " + token.toString() + " ] undeclare");
        }

        StmtNode assignStmt = null;
        if(this.look.tag == '=') {
            move();
            assignStmt = new SetStmt(id, bool());
        }
        // 数组赋值
        else {
            ArrayAccess x = arrayAccess(id);
            match('=');
            assignStmt = new SetElemStmt(x, bool());
        }

        match(';');
        return assignStmt;
    }

    /**
     * 处理 if 语句
     *
     * @return
     */
    private StmtNode ifStmt() throws IOException {
        List<StmtNode> ifBody = new ArrayList(), elseBody = null;
        match(Tag.IF);
        match('(');
        ExprNode boolExpr = bool();     // 解析布尔表达式
        match(')');
        stmt(ifBody);
        if (this.look.tag == Tag.ELSE) {
            elseBody = new ArrayList();
            stmt(elseBody);
        }

//        move();
        return new IfStmt(boolExpr, ifBody, elseBody);
    }

    /**
     * 处理变量声明
     * decls -----> type id   decls（非终结符）
     */
    public void decls() throws IOException {
        /**
         * 如果是变量声明,，每次解析一个变量声明
         */
        while (look.tag == Tag.BASIC) {
            Type type = getType();
            Token word = this.look;
            match(Tag.ID);
            match(';');

            IdNode idNode = new IdNode(word, type, offset);
            curTable.put(word, idNode);
            offset = offset + type.weight;
        }

    }

    /**
     * 获取类型，在上一个方法已经验证当前 token 是一个类型
     *
     * @return
     */
    private Type getType() throws IOException {
        Type basicType = (Type) this.look;    // 基础类型 int char float bool
        move();
        if (this.look.tag != '[') {
            return basicType;
        }
        // 解析数组
        return getArrayType(basicType);
    }

    /**
     * 数组的定义：  basicType[num]
     *
     * @param basicType
     * @return
     * @throws IOException
     */
    private Type getArrayType(Type basicType) throws IOException {
        move();
        Token num = this.look;
        /**
         *  这里会有一点小问题 ，基于之前 lex 时，real 类型
         *  的 tag 也是 num
         *  TODO:  区分实数和整数
         */
        match(Tag.NUM);
        match(']');
        // 如果是多维数组
        if (this.look.tag == '[') {
            basicType = getArrayType(basicType);
        }

        return new Array(basicType, ((Num) num).value);
    }


    /**
     * 获取 token，如果缓存中存在未消费的token，
     * 优先从缓存中获取
     *
     * @return
     * @throws IOException
     */
    public Token getToken() throws IOException {
        if (tokenBuf.size() > 0) {
            return tokenBuf.remove(tokenBuf.size() - 1);     // remove
        }

        return lexer.nextToken();
    }

    /**
     * 将未消费完成 token 存放到缓存中
     */
    public void unGetToken() {
        tokenBuf.add(this.look);
        this.look = null;
    }

    public void move() throws IOException {
        this.look = this.getToken();
    }


    public void match(int t) throws IOException {
        if (this.look.tag != t) {
            error("syntax error， expect " + ((char) t));
        }

        move();
    }

    /**
     * 当前的错误处理是一旦遇到语法错误就返回
     *
     * @param errMsg
     */
    private void error(String errMsg) {
        throw new RuntimeException("near line " + Lexer.line + ": " + errMsg);
    }


    /******************* 递归处理优先级高的运算符 **************************/

    /**
     * 解析 bool 表达式
     * bool ----->   join || bool
     * join
     *
     * @return
     */
    private ExprNode bool() throws IOException {
        ExprNode x = join();
        while (this.look.tag == Tag.OR) {
            Token op = this.look;
            move();
            x = new OrExpr(op, x, join());
        }
        return x;
    }

    /**
     * join  ---->   equality && joiin
     * equality
     *
     * @return
     * @throws IOException
     */
    private ExprNode join() throws IOException {
        ExprNode x = equality();
        while (this.look.tag == Tag.AND) {
            Token op = this.look;
            move();
            x = new AndExpr(op, x, equality());
        }
        return x;
    }

    /**
     * equality --- > rel  == equality
     * rel
     *
     * @return
     * @throws IOException
     */
    private ExprNode equality() throws IOException {
        ExprNode x = rel();
        while (this.look.tag == Tag.EQ || this.look.tag == Tag.NE) {
            Token op = this.look;
            move();
            x = new AndExpr(op, x, rel());
        }
        return x;
    }

    /**
     * TODO 暂时不支持非空为真或者非零为真的判断
     *
     * @return
     * @throws IOException
     */
    private ExprNode rel() throws IOException {
        ExprNode x = expr();
        switch (this.look.tag) {
            case '<':
            case '>':
            case Tag.GE:
            case Tag.LE:
                Token op = this.look;
                move();
                return new RelExpr(op, x, expr());
            default:
                return x;
        }
    }

    private ExprNode expr() throws IOException {
        ExprNode x = term();
        while (this.look.tag == '+' || this.look.tag == '-') {
            Token op = this.look;
            move();
            x = new AirthExpr(op, x, term());
        }
        return x;
    }

    private ExprNode term() throws IOException {
        ExprNode x = unary();
        while (this.look.tag == '*' || this.look.tag == '/') {
            Token op = this.look;
            move();
            x = new AirthExpr(op, x, unary());
        }
        return x;
    }

    /**
     * 处理单目运算符
     *
     * @return
     * @throws IOException
     */
    private ExprNode unary() throws IOException {
        if (this.look.tag == '-') {
            move();
            return new UnaryExpr(KeyWord.minus, unary());
        } else if (this.look.tag == '!') {
            Token op = this.look;
            move();
            return new NotExpr(op, unary());
        } else {
            return factor();
        }
    }

    /**
     * factor -----> num  (正数)
     * real (实数)
     * id  (变量)
     * Bool.True
     * Bool.False
     * ( expr )
     *
     * @return
     */
    private ExprNode factor() throws IOException {
        ExprNode expr = null;
        switch (this.look.tag) {
            case Tag.NUM:
                expr = new ConstNode(this.look, Type.Int);
                move();
                return expr;

            case Tag.REAL:
                expr = new ConstNode(this.look, Type.Float);
                move();
                return expr;

            case Tag.TRUE:
                expr = ConstNode.True;
                move();
                return expr;

            case Tag.FALSE:
                expr = ConstNode.False;
                move();
                return expr;

            case Tag.ID:
                IdNode id = curTable.get(this.look);
                if (id == null) {
                    error("id [ " + this.look.toString() + " ] undeclare");
                }
                move();
                // 如果不是数组
                if (this.look.tag != '[') {
                    return id;
                }
                return arrayAccess(id);
            default:
                error("syntax error");
        }

        return null;
    }

    private ArrayAccess arrayAccess(IdNode id) throws IOException {
        Type type = id.type;    // 获取到数组类型
        match('[');
        ExprNode index = bool();
        match(']');
        /**
         *  因为数组的定义是一段连续的内存，所以需要计算实际的位置
         *  这里暂时不做数组越界的检查
         */
        Token multiply = new Token('*');
        Token add = new Token('+');

        /**
         *  考虑一维数组的情况，我们需要该基本类型的长度
         */
        type = ((Array)type).of;
        ExprNode location = new AirthExpr(multiply, index, new ConstNode(type.weight));
        // 如果是多维数组,  需要重新计算实际的位置
        while (this.look.tag == '[') {
            type = ((Array) type).of;
            match('[');
            index = bool();
            match(']');
            /**
             *  开始重新计算，首先计算当前维度的位置 ，再累加之前计算的其他维度的位置
             */
            ExprNode index2 = new AirthExpr(multiply, index, new ConstNode(type.weight));
            location = new AirthExpr(add, location, index2);
        }

        // ps: 循环结束之后，type 会变成基本类型
        return new ArrayAccess(id, location, type);
    }

}

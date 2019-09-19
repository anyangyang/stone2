package parts2.Main.lexer;

import parts2.Main.util.ListUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * ReadBuffer 的主要作用从文件输入流中读取字符
 */
public class ReadBuffer {

    public Reader reader;

    /**
     *  字符缓存大小
     */
    public static final Integer BUF_SIZE = 8;
    /**
     *  字符缓存，这里直接使用 java 的容器 list 作为字符缓存
     *  当然你也可以使用数组加两个指针构建一个循环队列
     *  或者可以扩容的数组来实现
     */
    public List<Integer> buf = new ArrayList<Integer>(BUF_SIZE);
    /**
     *  当前行号
     */
    public static int lineNum = 0;    // 当前所在行号

    /**
     *  指向当前字符
     */
    public int peek = -1;

    public ReadBuffer(Reader reader) {
        this.reader = reader;
    }

    /**
     *  如果字符缓存中存在字符，优先从字符缓存中读取一个字符
     *  否则从输入流中读取一个字符
     *
     * @return
     * @throws IOException
     */
    public void getChar() throws IOException {
        if(ListUtil.isBlank(buf)) {
            this.peek = reader.read();
            return;
        }

        this.peek = buf.remove(buf.size() - 1);
    }

    /**
     * 判断下一个字符是否是期待的字符
     *
     * @param ch
     * @return
     */
    public boolean expect(int ch) throws IOException {
        this.getChar();
        if (this.peek == ch) {
            return true;
        }

        /**
         *  未消费掉的字符需要放入字符缓存中
         *  下一次读取字符时，先从缓存中获取
         */
        unGetChar();
        return false;
    }

    /**
     *  将 peek 指向的字符信息，存到字符缓存中
     * 并且重置 peek 的值
     */
    public void unGetChar() {
        buf.add(this.peek);
        this.peek = -1;
    }

}

package main.lex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferReader {
    private static final int BUFF_SIZE = 4;
    // 用于缓存从文件中读取的字符
   private List<Integer> buf = new ArrayList<Integer>(BUFF_SIZE);
   // 输入流
    private FileInputStream reader;
    // 当前正在读取的行
    public static int line = 1;
    // 前看字符
    public int peek = -1;

    public BufferReader(FileInputStream reader) {
        this.reader = reader;
    }
    /**
     * 读取一个字符
     *
     * @return
     */
    public void getChar() throws IOException {
        this.peek = buf.size() > 0
                            ?  buf.remove(buf.size() - 1)
                            : reader.read();
    }

    public void ungetCh() {
        buf.add(this.peek);
        this.peek = -1;
    }


    public boolean next(int expectCh) throws IOException {
         getChar();
         if(this.peek != expectCh) {
             return false;
         }

         this.peek = -1;
         return true;
    }
}

package main.lexer;

import main.constants.CommonConst;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferReader {

    // 用于缓存从文件中读取的字符
   private List<Integer> buf = new ArrayList<Integer>(CommonConst.BUFF_SIZE);
   // 文件
    private String infile;
   // 输入流
    private FileInputStream reader;
    // 当前正在读取的行
    public static int line = 1;
    // 前看字符
    public int peek = -1;

    public BufferReader(FileInputStream reader) {
        this.reader = reader;
    }

    public BufferReader(String infile) throws IOException{
        if (StringUtils.isBlank(infile)) {
            throw new RuntimeException("infile is null");
        }

        File file = new File(infile);
        if (!file.exists()) {
            throw new RuntimeException("infile [ " + infile + " ] not found");
        }

        this.reader = new FileInputStream(file);
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
         Boolean match = true;
         if(this.peek != expectCh) {
             ungetCh();
             match = false;
         }

         this.peek = -1;
         return match;
    }
}

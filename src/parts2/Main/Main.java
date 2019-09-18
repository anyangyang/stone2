package parts2.Main;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *  stone2 的 parts2 主要参考《两周自制脚本语言》
 */
public class Main {

    private static final String lexTestFileName = "E:\\stone2\\src\\parts2\\example";

    public static void main(String[] args) throws Exception{
        FileInputStream reader = initReader(lexTestFileName);
        try{
            // 开始设计词法分析器

            // 递归下降的语法分析器

            // 解释执行

        } finally {
            if(reader != null) {
                reader.close();
            }
        }
    }

    public static FileInputStream initReader(String infile) throws IOException {
        if (StringUtils.isBlank(infile)) {
            throw new RuntimeException("file name is null");
        }

        File file = new File(infile);
        if (!file.exists()) {
            throw new RuntimeException("file [ " + infile + " ] not found");
        }

       return  new FileInputStream(file);
    }
}

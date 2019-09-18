package parts2.Main;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception{
        FileInputStream reader = initReader("");
        try{


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

package parts2.Main.util;

import java.util.List;

public abstract class ListUtil {

    public static boolean isBlank(List list) {
        if(list == null || list.size() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNotBlank(List list) {
        return !isBlank(list);
    }
}

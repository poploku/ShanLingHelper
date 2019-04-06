package cc.lgiki.shanlinghelper.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextUtil {
    public static String timestampToString(Long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timestamp * 1000));
    }

    public static String convertByteToMegabyte(Long bytes) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(bytes / 1024 / 1024.0);
    }
}

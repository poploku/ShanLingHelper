package cc.lgiki.shanlinghelper.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

    public static String urlEncode(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String urlDncode(String str) {
        String result = null;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

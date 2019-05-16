package cc.lgiki.shanlinghelper.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.fragment.app.FragmentActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextUtil {
    public static String timestampInSecondToString(Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(new Date(timestamp * 1000));
        } else {
            return null;
        }
    }

    public static String timestamInMillisecondToString(Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(new Date(timestamp));
        } else {
            return null;
        }
    }

    public static String convertByteToMegabyte(Long bytes) {
        if (bytes != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            return decimalFormat.format(bytes / 1024 / 1024.0);
        } else {
            return null;
        }
    }

    public static String urlEncode(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String urlDecode(String str) {
        String result = str;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

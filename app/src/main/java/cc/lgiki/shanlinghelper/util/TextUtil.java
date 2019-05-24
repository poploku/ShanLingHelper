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
import java.util.Locale;

public class TextUtil {
    public static String timestampInSecondToString(Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return simpleDateFormat.format(new Date(timestamp * 1000));
        } else {
            return null;
        }
    }

    public static String timestamInMillisecondToString(Long timestamp) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return simpleDateFormat.format(new Date(timestamp));
        } else {
            return null;
        }
    }

    public static String convertByteToMegabyte(Long bytes) {
        if (bytes != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            StringBuilder stringBuilder = new StringBuilder(decimalFormat.format((double) bytes / 1024 / 1024));
            if (stringBuilder.toString().startsWith(".")) {
                stringBuilder.insert(0, "0");
            }
            return stringBuilder.toString();
        } else {
            return null;
        }
    }
}

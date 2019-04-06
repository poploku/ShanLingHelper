package cc.lgiki.shanlinghelper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean isIPAddress(String ip) {
        if (ip.length() < 7 || ip.length() > 15 || "".equals(ip)) {
            return false;
        }
        final String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}

package cc.lgiki.shanlinghelper.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    public final static String WIFI_TRANSFER_URL_KEY = "url";
    public final static String MAIN_PREFERENCES_NAME = "config";
    private static SharedPreferencesUtil sharedPreferencesUtil;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPreferencesUtil(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context, String name) {
        if (sharedPreferencesUtil == null) {
            sharedPreferencesUtil = new SharedPreferencesUtil(context, name);
        }
        return sharedPreferencesUtil;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }

}

package cc.lgiki.shanlinghelper;

import android.app.Application;

public class MyApplication extends Application {
    private static String shanLingWiFiTransferBaseUrl;

    public static String getShanLingWiFiTransferBaseUrl() {
        return shanLingWiFiTransferBaseUrl;
    }

    public static void setShanLingWiFiTransferBaseUrl(String shanLingWiFiTransferBaseUrl) {
        MyApplication.shanLingWiFiTransferBaseUrl = shanLingWiFiTransferBaseUrl;
    }
}

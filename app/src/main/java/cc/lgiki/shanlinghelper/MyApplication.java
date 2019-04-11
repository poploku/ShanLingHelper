package cc.lgiki.shanlinghelper;

import android.app.Application;

import net.gotev.uploadservice.UploadService;

public class MyApplication extends Application {
    private static String shanLingWiFiTransferBaseUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        UploadService.NAMESPACE = "cc.lgiki.shanlinghelper";
    }

    public static String getShanLingWiFiTransferBaseUrl() {
        return shanLingWiFiTransferBaseUrl;
    }

    public static void setShanLingWiFiTransferBaseUrl(String shanLingWiFiTransferBaseUrl) {
        MyApplication.shanLingWiFiTransferBaseUrl = shanLingWiFiTransferBaseUrl;
    }
}

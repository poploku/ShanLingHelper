package cc.lgiki.shanlinghelper.network;

import android.util.Log;

import cc.lgiki.shanlinghelper.MyApplication;
import cc.lgiki.shanlinghelper.util.HttpUtil;
import cc.lgiki.shanlinghelper.util.TextUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ShanlingWiFiTransferRequest {
    public static boolean getFileList(String path, okhttp3.Callback callback) {
        String shanLingWiFiTransferBaseUrl = MyApplication.getShanLingWiFiTransferBaseUrl();
        if (shanLingWiFiTransferBaseUrl == null) {
            return false;
        }
        String url = shanLingWiFiTransferBaseUrl + "list?path=" + path;
        HttpUtil.sendOkHttpRequest(url, callback);
        return true;
    }

    public static boolean createFolder(String currentPath, String folderName, okhttp3.Callback callback) {
        if (!currentPath.endsWith("%2F")) {
            currentPath += "%2F";
        }
        String path = currentPath + TextUtil.urlEncode(folderName);
        String shanLingWiFiTransferBaseUrl = MyApplication.getShanLingWiFiTransferBaseUrl();
        if (shanLingWiFiTransferBaseUrl == null) {
            return false;
        }
        String url = shanLingWiFiTransferBaseUrl + "create";
        RequestBody requestBody = new FormBody.Builder()
                .add("path", path)
                .build();
        HttpUtil.sendOkHttpRequest(url, requestBody, callback);
        return true;
    }
}

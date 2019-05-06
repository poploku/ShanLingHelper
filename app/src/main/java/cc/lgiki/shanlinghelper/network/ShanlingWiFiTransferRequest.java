package cc.lgiki.shanlinghelper.network;

import cc.lgiki.shanlinghelper.MyApplication;
import cc.lgiki.shanlinghelper.util.HttpUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

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
        if (!currentPath.endsWith("/")) {
            currentPath += "/";
        }
        String path = currentPath + folderName;
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

    public static boolean delete(String deleteFilePath, okhttp3.Callback callback) {
        String shanLingWiFiTransferBaseUrl = MyApplication.getShanLingWiFiTransferBaseUrl();
        if (shanLingWiFiTransferBaseUrl == null) {
            return false;
        }
        String url = shanLingWiFiTransferBaseUrl + "delete";
        RequestBody requestBody = new FormBody.Builder()
                .add("path", deleteFilePath)
                .build();
        HttpUtil.sendOkHttpRequest(url, requestBody, callback);
        return true;
    }

    public static boolean move(String oldPath, String newPath, okhttp3.Callback callback) {
        String shanLingWiFiTransferBaseUrl = MyApplication.getShanLingWiFiTransferBaseUrl();
        if (shanLingWiFiTransferBaseUrl == null) {
            return false;
        }
        String url = shanLingWiFiTransferBaseUrl + "move";
        RequestBody requestBody = new FormBody.Builder()
                .add("oldPath", oldPath)
                .add("newPath", newPath)
                .build();
        HttpUtil.sendOkHttpRequest(url, requestBody, callback);
        return true;
    }
}

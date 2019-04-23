package cc.lgiki.shanlinghelper.util;

import android.webkit.MimeTypeMap;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}

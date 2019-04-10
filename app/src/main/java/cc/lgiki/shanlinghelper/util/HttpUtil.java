package cc.lgiki.shanlinghelper.util;

import android.webkit.MimeTypeMap;

import java.io.File;

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

    public static Boolean uploadFile(String serverUrl, String path, File file, okhttp3.Callback callback) {
        try {
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("files[]", file.getName(),
                            RequestBody.create(MediaType.parse(getMimeType(file.getAbsolutePath())), file))
                    .addFormDataPart("path", path)
                    .build();
            Request request = new Request.Builder()
                    .url(serverUrl)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(callback);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}

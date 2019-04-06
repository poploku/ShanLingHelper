package cc.lgiki.shanlinghelper.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    public static void showShortToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showShortToast(Context context, int contentRes) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(contentRes), Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getResources().getString(contentRes));
        }
        toast.show();
    }

    public static void showLongToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showLongToast(Context context, int contentRes) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(contentRes), Toast.LENGTH_LONG);
        } else {
            toast.setText(context.getResources().getString(contentRes));
        }
        toast.show();
    }
}

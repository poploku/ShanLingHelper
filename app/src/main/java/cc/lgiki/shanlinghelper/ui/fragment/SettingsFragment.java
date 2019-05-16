package cc.lgiki.shanlinghelper.ui.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;

import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.util.SharedPreferencesUtil;
import cc.lgiki.shanlinghelper.util.ToastUtil;


public class SettingsFragment extends PreferenceFragmentCompat {
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(SharedPreferencesUtil.MAIN_PREFERENCES_NAME);
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }

    private void initData() {
        context = getActivity();
    }

    private void initView() {
        String appVersionName = getAppVersionName();
        findPreference("version").setSummary(appVersionName == null ? "Unknown" : appVersionName);
        findPreference("author").setOnPreferenceClickListener((preference) -> {
            ToastUtil.showShortToast(context, "❤");
            return true;
        });
    }

    private String getAppVersionName() {
        String version = null;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            PackageManager packageManager = activity.getPackageManager();
            try {
                PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
                version = packInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return version;
    }
}

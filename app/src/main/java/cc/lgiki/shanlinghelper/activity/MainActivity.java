package cc.lgiki.shanlinghelper.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import cc.lgiki.shanlinghelper.MyApplication;
import cc.lgiki.shanlinghelper.adapter.ShanLingFileListAdapter;
import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.model.ShanLingFileModel;
import cc.lgiki.shanlinghelper.network.ShanlingWiFiTransferRequest;
import me.rosuh.filepicker.config.FilePickerManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import cc.lgiki.shanlinghelper.util.RegexUtil;
import cc.lgiki.shanlinghelper.util.SharedPreferencesUtil;
import cc.lgiki.shanlinghelper.util.ToastUtil;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class MainActivity extends AppCompatActivity implements EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {
    private final String DEFAULT_PATH = "/mnt/mmc/";
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout shanLingFileListSwipeRefreshLayout;
    private TextView currentPathTextView;
    private List<ShanLingFileModel> shanLingFileModelList;
    private String shanLingWiFiTransferBaseUrl;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Stack<String> pathStack;
    private ShanLingFileListAdapter shanLingFileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, this.getResources().getString(R.string.permission_rationale), 1, permissions);
        }
        initData();
        initView();
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this, "config");
        shanLingWiFiTransferBaseUrl = sharedPreferencesUtil.getString("url");
        if (shanLingWiFiTransferBaseUrl == null || "".equals(shanLingWiFiTransferBaseUrl)) {
            showWiFiTransferUrlDialog();
        } else {
            MyApplication.setShanLingWiFiTransferBaseUrl(shanLingWiFiTransferBaseUrl);
            refreshShanLingFileList(pathStack.peek());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected: ");
        int itemId = item.getItemId();
        int position = shanLingFileListAdapter.getPosition();
        switch (itemId) {
            case R.id.menu_delete:
                break;
            case R.id.menu_rename:
                break;
            default:
                break;
        }
        return true;
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.tb_main);
        drawerLayout = findViewById(R.id.dl_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        RecyclerView shanLingFileListRecyclerView = findViewById(R.id.rv_shanling_file_list);
        FloatingActionButton uploadButton = findViewById(R.id.fab_upload_here);
        shanLingFileListSwipeRefreshLayout = findViewById(R.id.srl_shanling_file_list);
        currentPathTextView = findViewById(R.id.tv_current_path);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        shanLingFileListSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        navigationView.setCheckedItem(R.id.nav_upload);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                    drawerLayout.closeDrawers();
                    return true;
                }
        );
        shanLingFileListSwipeRefreshLayout.setOnRefreshListener(() -> refreshShanLingFileList(pathStack.peek()));
        registerForContextMenu(shanLingFileListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shanLingFileListRecyclerView.setLayoutManager(layoutManager);
        shanLingFileListAdapter = new ShanLingFileListAdapter(this, shanLingFileModelList);
        shanLingFileListAdapter.setOnItemClickListener((view, position) -> {
                    ShanLingFileModel shanLingFileModel = shanLingFileModelList.get(position);
                    String newPath = shanLingFileModel.getPath();
                    if (newPath != null && !pathStack.peek().equals(newPath)) {
                        pathStack.push(newPath);
                    }
                    refreshShanLingFileList(pathStack.peek());
                }
        );
        shanLingFileListRecyclerView.setAdapter(shanLingFileListAdapter);
        uploadButton.setOnClickListener((v) -> FilePickerManager.INSTANCE.from(this).forResult(FilePickerManager.REQUEST_CODE));
    }


    private void showRenameDialog(String oldFileName) {
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_input, null, false);
        TextFieldBoxes textFieldBoxes = view.findViewById(R.id.text_field_boxes);
        ExtendedEditText extendedEditText = view.findViewById(R.id.extended_edit_text);
        textFieldBoxes.setLabelText(getResources().getString(R.string.message_new_file_name));
        if (oldFileName != null) {
            extendedEditText.setText(oldFileName);
        }
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.title_input_new_file_name)
                .setCancelable(true)
                .setPositiveButton(R.string.btn_ok, (dialog, which) -> {
                    //TODO: complete file rename action
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private void showNewFolderDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_input, null, false);
        TextFieldBoxes textFieldBoxes = view.findViewById(R.id.text_field_boxes);
        ExtendedEditText extendedEditText = view.findViewById(R.id.extended_edit_text);
        textFieldBoxes.setLabelText(getResources().getString(R.string.message_new_folder_name));
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.title_input_new_folder_name)
                .setCancelable(true)
                .setPositiveButton(R.string.btn_ok, ((dialogInterface, i) -> {
                    String folderName = extendedEditText.getText().toString().trim();
                    boolean requestResult = ShanlingWiFiTransferRequest.createFolder(pathStack.peek(), folderName, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(() -> ToastUtil.showShortToast(MainActivity.this, R.string.message_create_folder_error));
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {
                                runOnUiThread(() -> {
                                    ToastUtil.showShortToast(MainActivity.this, R.string.message_create_folder_success);
                                    refreshShanLingFileList(pathStack.peek());
                                });
                            } else {
                                runOnUiThread(() -> ToastUtil.showShortToast(MainActivity.this, R.string.message_create_folder_error));
                            }
                        }
                    });
                    if (!requestResult) {
                        runOnUiThread(() -> ToastUtil.showShortToast(MainActivity.this, R.string.message_create_folder_error));
                    }
                }))
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }


    private void showWiFiTransferUrlDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_input, null, false);
        TextFieldBoxes textFieldBoxes = view.findViewById(R.id.text_field_boxes);
        ExtendedEditText extendedEditText = view.findViewById(R.id.extended_edit_text);
        textFieldBoxes.setMaxCharacters(15);
        textFieldBoxes.setLabelText("URL");
        extendedEditText.setPrefix("http://");
        extendedEditText.setSuffix(":8888");
        new AlertDialog.Builder(this)
                .setTitle(R.string.hint_enter_shanling_url)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_ok, ((dialog, which) -> {
                    String shanLingWiFiTransferIp = extendedEditText.getText().toString();
                    if (!RegexUtil.isIPAddress(shanLingWiFiTransferIp)) {
                        ToastUtil.showShortToast(this, R.string.message_shanling_url_error);
                        showWiFiTransferUrlDialog();
                    } else {
                        this.shanLingWiFiTransferBaseUrl = "http://" + shanLingWiFiTransferIp + ":8888/";
                        sharedPreferencesUtil.putString("url", shanLingWiFiTransferBaseUrl);
                        MyApplication.setShanLingWiFiTransferBaseUrl(shanLingWiFiTransferBaseUrl);
                        refreshShanLingFileList(pathStack.peek());
                    }
                }))
                .setNegativeButton(R.string.btn_cancel, ((dialog, which) -> {
                    ToastUtil.showLongToast(this, R.string.message_no_enter_shanling_url);
                    finish();
                }))
                .show();
    }

    private void initData() {
        shanLingFileModelList = new ArrayList<>();
        pathStack = new Stack<>();
        pathStack.push(DEFAULT_PATH);
    }

    @Override
    public void onBackPressed() {
        if (pathStack.size() > 1) {
            pathStack.pop();
            refreshShanLingFileList(pathStack.peek());
        } else {
            super.onBackPressed();
        }
    }

    private void refreshShanLingFileList(String path) {
        shanLingFileListSwipeRefreshLayout.setRefreshing(true);
        boolean requestResult = ShanlingWiFiTransferRequest.getFileList(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    ToastUtil.showShortToast(MainActivity.this, R.string.message_connect_shanling_wifi_transfer_error);
                    showWiFiTransferUrlDialog();
                });
                shanLingFileListSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseString = response.body().string();
                    JsonParser parser = new JsonParser();
                    JsonArray rootJsonArray = parser.parse(responseString).getAsJsonArray();
                    Gson gson = new Gson();
                    shanLingFileModelList.clear();
                    for (JsonElement element : rootJsonArray) {
                        ShanLingFileModel shanLingFileModel = gson.fromJson(element, new TypeToken<ShanLingFileModel>() {
                        }.getType());
                        shanLingFileModelList.add(shanLingFileModel);
                    }
                    Collections.sort(shanLingFileModelList);
                    runOnUiThread(() -> shanLingFileListAdapter.notifyDataSetChanged());
                    shanLingFileListSwipeRefreshLayout.setRefreshing(false);
                    currentPathTextView.setText(String.format(getResources().getString(R.string.message_current_path), pathStack.peek()));
                } catch (JsonSyntaxException | IllegalStateException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> ToastUtil.showShortToast(MainActivity.this, R.string.message_shanling_file_json_parse_error));
                    pathStack.pop();
                    if (!pathStack.isEmpty()) {
                        refreshShanLingFileList(pathStack.peek());
                    }
                    shanLingFileListSwipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    shanLingFileListSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        if (!requestResult) {
            ToastUtil.showShortToast(MainActivity.this, R.string.message_connect_shanling_wifi_transfer_error);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FilePickerManager.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    List<String> selectedFiles = FilePickerManager.INSTANCE.obtainData();
                    UploadActivity.actionStart(this, pathStack.peek(), selectedFiles);
                } else {
                    ToastUtil.showShortToast(this, R.string.message_no_select_file);
                }
                break;
            case UploadActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    refreshShanLingFileList(pathStack.peek());
                }
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_exit:
                finish();
                break;
            case R.id.menu_new_folder:
                showNewFolderDialog();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtil.showLongToast(this, R.string.message_no_permission);
        finish();
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {
        ToastUtil.showLongToast(this, R.string.message_no_permission);
        finish();
    }
}

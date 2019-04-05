package cc.lgiki.shanlinghelper.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.ShanLingFileListAdapter;
import cc.lgiki.shanlinghelper.R;
import model.ShanLingFileModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import util.HttpUtil;
import util.ToastUtil;

public class MainActivity extends AppCompatActivity implements EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView shanLingFileListRecyclerView;
    private ShanLingFileListAdapter shanLingFileListAdapter;
    private List<ShanLingFileModel> shanLingFileModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, this.getResources().getString(R.string.permission_rationale), 1, permissions);
        }
        initView();
        View view = LayoutInflater.from(this).inflate(R.layout.alertdialog_shanling_url_input, null);
        ExtendedEditText extendedEditText = view.findViewById(R.id.extended_edit_text);
        new AlertDialog.Builder(this)
                .setTitle(R.string.hint_enter_shanling_url)
                .setView(view)
                .setPositiveButton(R.string.btn_ok, ((dialog, which) -> {
                    ToastUtil.showLongToast(this, extendedEditText.getText().toString());
                }))
                .setNegativeButton(R.string.btn_cancel, ((dialog, which) -> {
                    ToastUtil.showLongToast(this, R.string.message_no_enter_shanling_url);
                    finish();
                }))
                .show();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        shanLingFileListRecyclerView = (RecyclerView) findViewById(R.id.rv_shanling_file_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_upload);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                    drawerLayout.closeDrawers();
                    return true;
                }
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shanLingFileListRecyclerView.setLayoutManager(layoutManager);
        shanLingFileListAdapter = new ShanLingFileListAdapter(this, shanLingFileModelList);
        shanLingFileListRecyclerView.setAdapter(shanLingFileListAdapter);
    }

    private void getShanLingFileList(String path) {
        HttpUtil.sendOkHttpRequest("", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
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

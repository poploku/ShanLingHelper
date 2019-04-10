package cc.lgiki.shanlinghelper.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.lgiki.shanlinghelper.MyApplication;
import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.adapter.UploadFileListAdapter;
import cc.lgiki.shanlinghelper.util.TextUtil;
import cc.lgiki.shanlinghelper.util.ToastUtil;

public class UploadActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private List<String> uploadFilePathList;
    private String uploadPath;
    private String shanLingWiFiTransferBaseUrl;
    private FloatingActionButton submitUploadButton;
    private RecyclerView uploadFileListRecyclerView;
    private UploadFileListAdapter uploadFileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        uploadFilePathList = intent.getStringArrayListExtra("uploadFilePathList");
        uploadPath = intent.getStringExtra("uploadPath");
        initView();
        initData();
    }

    private void initView() {
        TextView uploadPathTextView = (TextView) findViewById(R.id.tv_upload_to_path);
        toolbar = (Toolbar) findViewById(R.id.tb_upload);
        uploadFileListRecyclerView = (RecyclerView) findViewById(R.id.rv_upload_file_list);
        submitUploadButton = (FloatingActionButton) findViewById(R.id.fab_upload_submit);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_upload_activity);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uploadFileListRecyclerView.setLayoutManager(layoutManager);
        uploadFileListAdapter = new UploadFileListAdapter(this, uploadFilePathList);
        uploadFileListRecyclerView.setAdapter(uploadFileListAdapter);
        uploadPathTextView.setText(String.format(getResources().getString(R.string.message_file_will_upload_to), TextUtil.urlDncode(uploadPath)));
        submitUploadButton.setOnClickListener((v -> {
            //TODO: Complete file upload using OKHttp
        }));
    }

    private void initData() {
        shanLingWiFiTransferBaseUrl = MyApplication.getShanLingWiFiTransferBaseUrl();
        if (shanLingWiFiTransferBaseUrl == null) {
            ToastUtil.showShortToast(this, R.string.message_can_not_get_shanling_wifi_transfer_url);
            finish();
        }
    }

    public static void actionStart(Context context, String uploadPath, List<String> uploadFilePathList) {
        Intent intent = new Intent(context, UploadActivity.class);
        intent.putExtra("uploadPath", uploadPath);
        intent.putStringArrayListExtra("uploadFilePathList", new ArrayList<>(uploadFilePathList));
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}

package cc.lgiki.shanlinghelper.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.lgiki.shanlinghelper.MyApplication;
import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.adapter.UploadFileListAdapter;
import cc.lgiki.shanlinghelper.util.TextUtil;
import cc.lgiki.shanlinghelper.util.ToastUtil;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    public static final int REQUEST_CODE = 10180;
    private Toolbar toolbar;
    private List<String> uploadFilePathList;
    private String uploadPath;
    private String shanLingWiFiTransferBaseUrl;
    private FloatingActionButton submitUploadButton;
    private RecyclerView uploadFileListRecyclerView;
    private UploadFileListAdapter uploadFileListAdapter;
    private ProgressDialog uploadProgressDialog;
    private MultipartUploadRequest multipartUploadRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent = getIntent();
        uploadFilePathList = intent.getStringArrayListExtra("uploadFilePathList");
        uploadPath = intent.getStringExtra("uploadPath");
        uploadPath = TextUtil.urlDecode(uploadPath);
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
        uploadPathTextView.setText(String.format(getResources().getString(R.string.message_file_will_upload_to), uploadPath));
        submitUploadButton.setOnClickListener((v -> {
            if (uploadPath != null && !"".equals(uploadPath) && uploadFilePathList.size() > 0) {
                if (uploadProgressDialog == null) {
                    uploadProgressDialog = new ProgressDialog(UploadActivity.this);
                }
                uploadProgressDialog.setTitle(R.string.title_uploading);
                uploadProgressDialog.setCancelable(false);
                uploadProgressDialog.setMessage(getResources().getString(R.string.message_uploading));
                uploadProgressDialog.show();
                String serverUrl = shanLingWiFiTransferBaseUrl + "upload";
                UploadNotificationConfig notificationConfig = new UploadNotificationConfig();
                notificationConfig.getCompleted().autoClear = true;
                List<MultipartUploadRequest> multipartUploadRequestList = new ArrayList<>();
                List<String> uploadSuccessFileList = new ArrayList<>();
                List<String> uploadFailFileList = new ArrayList<>();
                for (String filePath : uploadFilePathList) {
                    try {
                        MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(UploadActivity.this, serverUrl);
                        multipartUploadRequest.setDelegate(new UploadStatusDelegate() {
                            private int currentIndex = uploadFilePathList.indexOf(filePath);
                            private int totalFiles = uploadFilePathList.size();
                            private String currentUploadFileName = new File(filePath).getName();

                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {
                                uploadProgressDialog.setMessage(String.format(getResources().getString(R.string.message_uploading_progress), currentUploadFileName, currentIndex + 1, totalFiles, uploadInfo.getUploadRateString()));
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                                uploadFailFileList.add(filePath);
                                uploadProgressDialog.cancel();
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                uploadSuccessFileList.add(filePath);
                                if (currentIndex + 1 == uploadFilePathList.size()) {
                                    uploadProgressDialog.cancel();
                                    ToastUtil.showShortToast(UploadActivity.this, String.format(getResources().getString(R.string.message_upload_success), uploadSuccessFileList.size()));
                                    setResult(Activity.RESULT_OK);
                                    finish();
                                } else {
                                    MultipartUploadRequest request = multipartUploadRequestList.get(currentIndex + 1);
                                    if (request != null) {
                                        request.startUpload();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                uploadFailFileList.add(filePath);
                                uploadProgressDialog.cancel();
                            }
                        });
                        multipartUploadRequest.addFileToUpload(filePath, "files[]")
                                .addParameter("path", uploadPath)
                                .setMaxRetries(2)
                                .setNotificationConfig(notificationConfig)
                                .setUtf8Charset();
                        multipartUploadRequestList.add(multipartUploadRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                MultipartUploadRequest request = multipartUploadRequestList.get(0);
                if (request != null) {
                    request.startUpload();
                }else{
                    //TODO: failed upload files
                }
            }
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
        ((AppCompatActivity) context).startActivityForResult(intent, REQUEST_CODE);
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

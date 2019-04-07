package cc.lgiki.shanlinghelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.holder.UploadFileItemViewHolder;

public class UploadFileListAdapter extends RecyclerView.Adapter<UploadFileItemViewHolder> {
    private Context context;
    private List<String> uploadFileList;


    @NonNull
    @Override
    public UploadFileItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upload_file, viewGroup, false);
        UploadFileItemViewHolder viewHolder = new UploadFileItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UploadFileItemViewHolder uploadFileItemViewHolder, int i) {
        int position = uploadFileItemViewHolder.getAdapterPosition();
        String uploadFilePath = uploadFileList.get(position);
        uploadFileItemViewHolder.uploadFilePath.setText(uploadFilePath);
    }

    @Override
    public int getItemCount() {
        return uploadFileList.size();
    }

    public UploadFileListAdapter(Context context, List<String> uploadFileList) {
        this.context = context;
        this.uploadFileList = uploadFileList;
    }
}

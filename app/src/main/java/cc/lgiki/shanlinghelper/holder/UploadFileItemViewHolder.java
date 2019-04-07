package cc.lgiki.shanlinghelper.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cc.lgiki.shanlinghelper.R;

public class UploadFileItemViewHolder extends RecyclerView.ViewHolder {
    public TextView uploadFilePath;

    public UploadFileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        uploadFilePath = (TextView) itemView.findViewById(R.id.tv_upload_file_path);
    }
}

package cc.lgiki.shanlinghelper.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cc.lgiki.shanlinghelper.R;

public class ShanLingFileItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView fileIcon;
    public TextView fileName;
    public TextView fileCreateTime;
    public TextView fileSize;

    public ShanLingFileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        fileIcon = (ImageView) itemView.findViewById(R.id.iv_file_icon);
        fileName = (TextView) itemView.findViewById(R.id.tv_file_name);
        fileCreateTime = (TextView) itemView.findViewById(R.id.tv_file_create_time);
        fileSize = (TextView) itemView.findViewById(R.id.tv_file_size);
    }
}

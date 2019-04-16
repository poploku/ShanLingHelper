package cc.lgiki.shanlinghelper.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.holder.ShanLingFileItemViewHolder;
import cc.lgiki.shanlinghelper.model.ShanLingFileModel;
import cc.lgiki.shanlinghelper.util.TextUtil;

public class UploadFileListAdapter extends ShanLingFileListAdapter {
    public UploadFileListAdapter(Context context, List<ShanLingFileModel> shanLingFileModelList) {
        super(context, shanLingFileModelList);
    }

    @Override
    public void onBindViewHolder(@NonNull ShanLingFileItemViewHolder shanLingFileItemViewHolder, int i) {
        int position = shanLingFileItemViewHolder.getAdapterPosition();
        ShanLingFileModel shanLingFileModel = shanLingFileModelList.get(position);
        String filePath = shanLingFileModel.getPath();
        String fileName = shanLingFileModel.getName();
        String fileLastModifiedDate = TextUtil.timestamInMillisecondToString(shanLingFileModel.getCtime());
        String fileSize = TextUtil.convertByteToMegabyte(shanLingFileModel.getSize()) + " MB";
        shanLingFileItemViewHolder.fileName.setText(fileName == null ? filePath : fileName);
        shanLingFileItemViewHolder.fileCreateTime.setText(fileLastModifiedDate == null ? context.getResources().getString(R.string.message_unknown_create_date) : fileLastModifiedDate);
        shanLingFileItemViewHolder.fileSize.setText(fileSize);
        int fileIconResourceId = getFileIconResourceId(shanLingFileModel);
        Glide.with(context)
                .load(fileIconResourceId)
                .into(shanLingFileItemViewHolder.fileIcon);
        shanLingFileItemViewHolder.itemView.setClickable(false);
    }
}

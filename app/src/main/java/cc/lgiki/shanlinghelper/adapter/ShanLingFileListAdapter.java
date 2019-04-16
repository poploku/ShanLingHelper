package cc.lgiki.shanlinghelper.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cc.lgiki.shanlinghelper.R;
import cc.lgiki.shanlinghelper.holder.ShanLingFileItemViewHolder;
import cc.lgiki.shanlinghelper.model.ShanLingFileModel;
import cc.lgiki.shanlinghelper.util.TextUtil;

public class ShanLingFileListAdapter extends RecyclerView.Adapter<ShanLingFileItemViewHolder> {
    Context context;
    List<ShanLingFileModel> shanLingFileModelList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ShanLingFileItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shanling_file, viewGroup, false);
        ShanLingFileItemViewHolder viewHolder = new ShanLingFileItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShanLingFileItemViewHolder shanLingFileItemViewHolder, int i) {
        int position = shanLingFileItemViewHolder.getAdapterPosition();
        ShanLingFileModel shanLingFileModel = shanLingFileModelList.get(position);
        shanLingFileItemViewHolder.fileName.setText(shanLingFileModel.getName());
        shanLingFileItemViewHolder.fileCreateTime.setText(TextUtil.timestampInSecondToString(shanLingFileModel.getCtime()));
        shanLingFileItemViewHolder.fileSize.setText("");
        if (shanLingFileModel.isFile()) {
            shanLingFileItemViewHolder.fileSize.setText(TextUtil.convertByteToMegabyte(shanLingFileModel.getSize()) + "MB");
        }
        int fileIconResourceId = getFileIconResourceId(shanLingFileModel);
        Glide.with(context)
                .load(fileIconResourceId)
                .into(shanLingFileItemViewHolder.fileIcon);
        shanLingFileItemViewHolder.itemView.setClickable(false);
        if (!shanLingFileModel.isFile()) {
            shanLingFileItemViewHolder.itemView.setClickable(true);
            shanLingFileItemViewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v, position));
        }
    }

    int getFileIconResourceId(ShanLingFileModel shanLingFileModel) {
        int resourceId = R.drawable.ic_folder;
        switch (shanLingFileModel.getFileType()) {
            case ShanLingFileModel.FILE_TYPE_MUSIC:
                resourceId = R.drawable.ic_music;
                break;
            case ShanLingFileModel.FILE_TYPE_PICTURE:
                resourceId = R.drawable.ic_picture;
                break;
            case ShanLingFileModel.FILE_TYPE_LYRIC:
                resourceId = R.drawable.ic_lyric;
                break;
            case ShanLingFileModel.FILE_TYPE_UNKNOWN:
                resourceId = R.drawable.ic_unknown;
                break;
            default:
                break;
        }
        return resourceId;
    }


    @Override
    public int getItemCount() {
        return shanLingFileModelList.size();
    }

    public ShanLingFileListAdapter(Context context, List<ShanLingFileModel> shanLingFileModelList) {
        this.context = context;
        this.shanLingFileModelList = shanLingFileModelList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

package cc.lgiki.shanlinghelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private static final String TAG = "ShanLingFileListAdapter";
    private Context context;
    private List<ShanLingFileModel> shanLingFileModelList;
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
        shanLingFileItemViewHolder.fileCreateTime.setText(TextUtil.timestampToString(shanLingFileModel.getCtime()));
        if (shanLingFileModel.isFile()) {
            shanLingFileItemViewHolder.fileSize.setText(TextUtil.convertByteToMegabyte(shanLingFileModel.getSize()) + "MB");
        }
        int fileIconResourceId = (shanLingFileModel.isFile()) ? R.drawable.ic_music : R.drawable.ic_folder;
        Glide.with(context)
                .load(fileIconResourceId)
                .into(shanLingFileItemViewHolder.fileIcon);
        shanLingFileItemViewHolder.itemView.setClickable(false);
        if (!shanLingFileModel.isFile()) {
            shanLingFileItemViewHolder.itemView.setClickable(true);
            shanLingFileItemViewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v, position));
        }

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

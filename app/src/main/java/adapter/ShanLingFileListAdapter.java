package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cc.lgiki.shanlinghelper.R;
import holder.ShanLingFileItemViewHolder;
import model.ShanLingFileModel;

public class ShanLingFileListAdapter extends RecyclerView.Adapter<ShanLingFileItemViewHolder> {
    private Context context;
    private List<ShanLingFileModel> shanLingFileModelList;

    @NonNull
    @Override
    public ShanLingFileItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shanling_file, viewGroup, false);
        ShanLingFileItemViewHolder viewHolder = new ShanLingFileItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShanLingFileItemViewHolder shanLingFileItemViewHolder, int i) {
        ShanLingFileModel shanLingFileModel = shanLingFileModelList.get(i);
        shanLingFileItemViewHolder.fileName.setText(shanLingFileModel.getName());
        int fileIconResourceId = (shanLingFileModel.getSize() != null) ? R.drawable.ic_music : R.drawable.ic_folder;
        Glide.with(context)
                .load(fileIconResourceId)
                .into(shanLingFileItemViewHolder.fileIcon);
    }

    @Override
    public int getItemCount() {
        return shanLingFileModelList.size();
    }

    public ShanLingFileListAdapter(Context context, List<ShanLingFileModel> shanLingFileModelList) {
        this.context = context;
        this.shanLingFileModelList = shanLingFileModelList;
    }
}

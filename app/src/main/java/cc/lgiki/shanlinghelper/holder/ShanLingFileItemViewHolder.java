package cc.lgiki.shanlinghelper.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cc.lgiki.shanlinghelper.R;

public class ShanLingFileItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    public ImageView fileIcon;
    public TextView fileName;
    public TextView fileCreateTime;
    public TextView fileSize;

    public ShanLingFileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        fileIcon = itemView.findViewById(R.id.iv_file_icon);
        fileName = itemView.findViewById(R.id.tv_file_name);
        fileCreateTime = itemView.findViewById(R.id.tv_file_create_time);
        fileSize = itemView.findViewById(R.id.tv_file_size);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, R.id.menu_delete, Menu.NONE, R.string.menu_delete);
        menu.add(Menu.NONE, R.id.menu_rename, Menu.NONE, R.string.menu_rename);
    }
}

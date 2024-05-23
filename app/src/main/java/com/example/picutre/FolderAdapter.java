package com.example.picutre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<FolderItem> folderItems;
    private Context context;


    public FolderAdapter(List<FolderItem> folderItems) {
        this.folderItems = folderItems;
    }


    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listview, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder, int position) {
        FolderItem folderItem = folderItems.get(position);
        holder.folderName.setText(folderItem.getFolderName());
        holder.count.setText(String.valueOf(folderItem.getCount()));
        Glide.with(context).load(folderItem.getFirstImagePath()).into(holder.folderImage);


    }

    @Override
    public int getItemCount() {
        return folderItems.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView folderName;
        ImageView folderImage;
        TextView count;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.name);
            folderImage = itemView.findViewById(R.id.imageview);
            count = itemView.findViewById(R.id.count);
        }
    }
}

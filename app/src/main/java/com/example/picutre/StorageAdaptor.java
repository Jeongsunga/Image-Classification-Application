package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StorageAdaptor extends RecyclerView.Adapter<StorageAdaptor.StorageItemViewHolder> {

    private List<StorageItem> storageItemList;

    public StorageAdaptor(List<StorageItem> storageItemList) {
        this.storageItemList = storageItemList;
    }

    @NonNull
    @Override
    public StorageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
        return new StorageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageItemViewHolder holder, int position) {
        StorageItem storageItem = storageItemList.get(position);
        holder.folderNameTextView.setText(storageItem.getFolderName2());
        holder.countTextView.setText(String.valueOf(storageItem.getCount2()));


        //holder.firstImageView.setImageResource(R.drawable.directory); // placeholder 이미지 설정
        if (storageItem.getFirstImagePath2() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(storageItem.getFirstImagePath2())
                    .into(holder.firstImageView);
            Log.e(TAG, "이미지의 경로 : ");
        } else {
            holder.firstImageView.setImageResource(R.drawable.clover); // placeholder 이미지 설정
        }
    }

    @Override
    public int getItemCount() {
        return storageItemList.size();
    }

    public static class StorageItemViewHolder extends RecyclerView.ViewHolder {
        TextView folderNameTextView;
        TextView countTextView;
        ImageView firstImageView;

        public StorageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.name);
            countTextView = itemView.findViewById(R.id.count);
            firstImageView = itemView.findViewById(R.id.imageview);
        }
    }
}

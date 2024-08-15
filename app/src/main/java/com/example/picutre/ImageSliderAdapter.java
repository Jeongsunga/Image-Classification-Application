package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>{

    private List<String> imageUrls;
    private Context context;
    private boolean isImageOne = true;
    private String metadataList;

    public ImageSliderAdapter(List<String> imageUrls, Context context, String metadataList) {
        this.imageUrls = imageUrls;
        this.context = context;
        this.metadataList = metadataList;
    }

    public void updateMetadata(String metadataList) {
        this.metadataList = metadataList;
        notifyDataSetChanged(); // Adapter에 데이터가 변경되었음을 알림
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_silder, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.clover)
                .error(R.drawable.nwh28)
                .into(holder.imageView);

        holder.btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (isImageOne) {
                        holder.btn_heart.setImageResource(R.drawable.fullheart); // 변경할 이미지
                    } else {
                        holder.btn_heart.setImageResource(R.drawable.heart); // 원래 이미지
                    }
                    // 상태 토글
                    isImageOne = !isImageOne;
            }
        });

        holder.btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadDialog();
            }
        });

        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "사진 정보 : " + metadataList);
                new AlertDialog.Builder(context)
                        .setTitle("사진 정보")
                        .setMessage(metadataList).show();
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton btn_heart, btn_info, btn_download, btn_delete;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            btn_heart = itemView.findViewById(R.id.btn_heart);
            btn_info = itemView.findViewById(R.id.btn_info);
            btn_download = itemView.findViewById(R.id.btn_download);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void showDownloadDialog() {
        new AlertDialog.Builder(context)
                .setTitle("다운로드")
                .setMessage("이 사진을 갤러리에 저장하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 아무 동작 수행 X
                    }
                }).show();
    }

    public void showDeleteDialog() {
        new AlertDialog.Builder(context)
                .setTitle("삭제")
                .setMessage("이 사진을 삭제하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 아묻 동작 수행 X
                    }
                }).show();
    }

}

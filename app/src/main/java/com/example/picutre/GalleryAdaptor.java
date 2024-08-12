package com.example.picutre;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;
public class GalleryAdaptor extends BaseAdapter {

    private List<String> imageUrls;
    private RequestManager glideRequestManager;
    private LayoutInflater inflater;
    private Context context;

    public GalleryAdaptor(Context context, List<String> imageUrls, RequestManager glideRequestManager){
        this.imageUrls = imageUrls;
        this.glideRequestManager = glideRequestManager;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    public int getCount() {
        return imageUrls.size();
    }

    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(imageView);
        } else imageView = (ImageView) convertView.getTag();

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageOne.class);
            intent.putStringArrayListExtra("imageUrls", new ArrayList<>(imageUrls));
            intent.putExtra("position", position); // 클릭된 이미지의 위치 전달
            context.startActivity(intent);
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        String imageUrl = imageUrls.get(position);
        glideRequestManager.load(imageUrl).into(imageView); // 이미지 로드
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}

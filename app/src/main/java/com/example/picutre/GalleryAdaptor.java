package com.example.picutre;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;

import java.util.List;
public class GalleryAdaptor implements ListAdapter {

    private List<String> imageUrls;
    private RequestManager glideRequestManager;
    private LayoutInflater inflater;

    public GalleryAdaptor(List<String> imageUrls, RequestManager glideRequestManager){
        this.imageUrls = imageUrls;
        this.glideRequestManager = glideRequestManager;
    }

    public GalleryAdaptor(Context context) {
        this.inflater = inflater.from(context);
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public RequestManager getGlideRequestManager() {
        return glideRequestManager;
    }

    public void setGlideRequestManager(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
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
        } else {
            imageView = (ImageView) convertView.getTag();
        }

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

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return ListAdapter.super.getAutofillOptions();
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

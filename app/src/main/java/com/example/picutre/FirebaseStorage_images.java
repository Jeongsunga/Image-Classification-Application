package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class FirebaseStorage_images extends AppCompatActivity {
    private GridView gridView;
    private FirebaseImageLoader firebaseImageLoader;
    private GalleryAdaptor galleryAdaptor;
    private RequestManager glideRequestManager;
    private TextView imagecount, foldername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firebase_storage_images);

        gridView = findViewById(R.id.gridView);
        glideRequestManager = Glide.with(this);
        imagecount = findViewById(R.id.imageCount);
        foldername = findViewById(R.id.foldername);



        // Intent에서 폴더 이름을 가져옵니다.
        Intent intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        int imageCount = intent.getIntExtra("imageCount", 0);

        foldername.setText(folderName);
        imagecount.setText(String.valueOf(imageCount));

        Log.d(TAG, "폴더 이름 : " + folderName + ", 사진 장 수 : " + imageCount);


        // FirebaseImageLoader 인스턴스를 생성하면서 폴더 이름을 전달합니다.
        firebaseImageLoader = new FirebaseImageLoader(folderName);
        Log.d(TAG, "ok!");

        firebaseImageLoader.fetchImageUrls(new FirebaseImageLoader.ImageUrlCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                galleryAdaptor = new GalleryAdaptor(urls, glideRequestManager);
                gridView.setAdapter(galleryAdaptor);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
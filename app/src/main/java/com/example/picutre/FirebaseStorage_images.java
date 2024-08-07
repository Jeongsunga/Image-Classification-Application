package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
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
import android.widget.Toast;

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
   // private Context context;


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


        firebaseImageLoader.fetchImageUrls(new FirebaseImageLoader.ImageUrlCallback() {
            //private Context context;

            @Override
            public void onSuccess(List<String> urls) {
                if(urls.isEmpty()) {
                    Log.d(TAG, "url이 null값 입니다.");
                }
                else {
                    Log.d(TAG, "the problem of the adaptor");
                    galleryAdaptor = new GalleryAdaptor(FirebaseStorage_images.this, urls, glideRequestManager);
                    gridView.setAdapter(galleryAdaptor);
                    Log.d(TAG, "ok!");
                }

            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(FirebaseStorage_images.this, "Failed to load images", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
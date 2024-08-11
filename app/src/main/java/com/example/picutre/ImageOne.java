package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageOne extends AppCompatActivity {

    private ImageView imageView;
    private Button btn_back;
    private Button btn_heart;
    private Button btn_download;
    private Button btn_info;
    private Button btn_delete;

    private ViewPager2 viewPager;
    private ImageSliderAdapter adapter;
    private List<String> imageUrls;
    private int initialPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_one);
        viewPager = findViewById(R.id.viewPager);
        //imageView = findViewById(R.id.oneImage);
//        btn_back = findViewById(R.id.btn_back);
//        btn_heart = findViewById(R.id.btn_heart);
//        btn_download = findViewById(R.id.btn_download);
//        btn_info = findViewById(R.id.btn_info);
//        btn_delete = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        //String imageUrl = intent.getStringExtra("imageUrl");
        imageUrls = intent.getStringArrayListExtra("imageUrls"); // 이미지 URL 리스트 받기
        initialPosition = intent.getIntExtra("position", 0); // 처음 표시할 이미지의 위치
        //Log.d(TAG, "imageURL : "+ imageUrls);

        adapter = new ImageSliderAdapter(imageUrls, ImageOne.this);
        viewPager.setAdapter(adapter);

        // 처음 표시할 이미지 설정
        viewPager.setCurrentItem(initialPosition);

//        if (imageUrl != null) {
//            Glide.with(this)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.clover) // 로딩 중 표시할 이미지
//                    .error(R.drawable.xSign) // 로딩 실패 시 표시할 이미지
//                    .into(imageView);
//        }//Glide.with(ImageOne.this).load(imageUrl).into(imageView);
//        else Toast.makeText(ImageOne.this, "no image", Toast.LENGTH_SHORT).show();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
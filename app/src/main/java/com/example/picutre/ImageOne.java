package com.example.picutre;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import androidx.annotation.NonNull;

import java.util.List;

public class ImageOne extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton btn_heart, btn_download, btn_info, btn_delete;

    private ViewPager2 viewPager;
    private ImageSliderAdapter adapter;
    private List<String> imageUrls;
    private int initialPosition;
    private boolean isImageOne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_image_one);

        // Firebase Storage 인스턴스 초기화
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // 스토리지에서 삭제할 파일의 참조를 가져옵니다. 파일 경로는 Firebase Storage의 경로를 사용합니다.
        // 예: "images/myPhoto.jpg"
        StorageReference storageRef = storage.getReference().child("images/myPhoto.jpg");

        viewPager = findViewById(R.id.viewPager);
        btn_heart = (ImageButton)findViewById(R.id.btn_heart);
        btn_download = (ImageButton)findViewById(R.id.btn_download);
        btn_info = (ImageButton)findViewById(R.id.btn_info);
        btn_delete = (ImageButton)findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        imageUrls = intent.getStringArrayListExtra("imageUrls"); // 이미지 URL 리스트 받기
        initialPosition = intent.getIntExtra("position", 0); // 처음 표시할 이미지의 위치

        adapter = new ImageSliderAdapter(imageUrls, ImageOne.this);
        viewPager.setAdapter(adapter);

        // 처음 표시할 이미지 설정
        viewPager.setCurrentItem(initialPosition);

        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageOne) {
                    btn_heart.setImageResource(R.drawable.fullheart); // 변경할 이미지
                } else {
                    btn_heart.setImageResource(R.drawable.heart); // 원래 이미지
                }
                // 상태 토글
                isImageOne = !isImageOne;
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownloadDialog();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDownloadDialog() {
        new AlertDialog.Builder(this)
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

                    }
                }).show();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
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

                    }
                }).show();
    }

}
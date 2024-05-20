package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;


import java.util.Random;

public class inAppGallery extends AppCompatActivity {

    private Button btn, btn_save;
    private EditText editText;
    private ImageView imageView;
    Random random = new Random();

    private  final int GALLERY_CODE = 10;
    //ImageView photo;
    private FirebaseStorage storage;

    private static final int REQUEST_GALLERY = 1001;
    private static final int REQUEST_CODE = 1;


    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(inAppGallery.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_app_gallery);

        btn = findViewById(R.id.btn);
        editText = findViewById(R.id.edittext_int);
        btn_save = findViewById(R.id.btn_save);
        imageView = findViewById(R.id.imageView);
        storage=FirebaseStorage.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = editText.getText().toString();
                int randomNum = random.nextInt(100);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //path : key 값, myRef : 데이터 값
                DatabaseReference myRef = database.getReference(String.valueOf(randomNum));
                //Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(randomNum),Toast.LENGTH_SHORT);
                //toast.show();
                myRef.setValue(str);
                Toast toast1 = Toast.makeText(getApplicationContext(), "DB에 저장되었습니다.",Toast.LENGTH_SHORT);
                toast1.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        // FirebaseStorage 인스턴스 가져오기
        //FirebaseStorage storage = FirebaseStorage.getInstance();

        // Firebase Storage의 기본 참조 가져오기
        //StorageReference storageRef = storage.getReference();

        // 현재 연결된 스토리지의 버킷 이름 가져오기
        //String bucketName = storageRef.getBucket();

        // 가져온 버킷 이름을 토스트 메시지로 출력
        //Toast.makeText(this, "현재 연결된 스토리지 버킷 이름: " + bucketName, Toast.LENGTH_LONG).show();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 여러 이미지 선택 허용
        startActivityForResult(intent, REQUEST_GALLERY);
    }

}
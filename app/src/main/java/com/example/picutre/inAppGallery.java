package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.NonNull;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class inAppGallery extends AppCompatActivity {

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

        /*
        // FirebaseStorage 인스턴스 가져오기
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Firebase Storage의 기본 참조 가져오기
        StorageReference storageRef = storage.getReference();

        // 현재 연결된 스토리지의 버킷 이름 가져오기
        String bucketName = storageRef.getBucket();

        // 가져온 버킷 이름을 토스트 메시지로 출력
        Toast.makeText(this, "현재 연결된 스토리지 버킷 이름: " + bucketName, Toast.LENGTH_LONG).show();
*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
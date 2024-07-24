package com.example.picutre;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import java.io.File;

public class LoadingScreen extends AppCompatActivity {

    private Button btn_next, btn_test;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String TAG = "LoadingScreen";
    private String folderPath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading_screen);

        btn_next = findViewById(R.id.btn_next);
        btn_test = findViewById(R.id.btn_test);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //권한 요청 코드
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        folderPath = getIntent().getStringExtra("folderPath");
        if (folderPath != null) {
            uploadImages(folderPath);
            //showDialogAutomatically(); // 다이얼로그 자동으로 띄우는 메소드
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 승인됨, 이미지 업로드 실행
            } else {
                //Toast.makeText(this, "외부 저장소 읽기 권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
                //showPermissionDialog();
                requestMediaPermissions(); // 미디어 접근 허용하도록 하기
                //Log.d(TAG, "권한 허용되었습니다.");
            }

            folderPath = getIntent().getStringExtra("folderPath");
            if (folderPath != null) {
                Log.d(TAG, "업로드 이미지");
                uploadImages(folderPath);
            }

        }
    }
    /*
    private void showDialogAutomatically() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoadingScreen.this);
        //builder.setTitle("권한 허");
        builder.setMessage("분류가 완료되었습니다.\n분류 결과를 확인하시겠습니까?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false); // 뒤로가기 버튼으로 다이얼로그 종료 못함
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(LoadingScreen.this, inAppGallery.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }*/

    private void requestMediaPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,

            }, PERMISSION_REQUEST_CODE);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
    }

    private void uploadImages(String folderPath) {
        File folder = new File(folderPath);
        Log.d(TAG, "folderPath" + folder);

        // 디렉터리 존재 여부 확인
        if (!folder.exists() || !folder.isDirectory()) {
            // 여기가 진짜 문제였음...
            Log.e(TAG, "디렉터리가 존재하지 않거나 디렉터리가 아닙니다: " + folderPath);
            return;
        }
        File[] files = folder.listFiles();

        if (files != null) { //파일이 널이 아니면 아래 실행
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    uploadImageToFirebase(file);
                }
            }
        }else {
            Log.e(TAG, "디렉터리에 파일이 없습니다: " + folderPath);
        }
    }

    private boolean isImageFile(String fileName) {
        String[] imageExtensions = {"jpg", "jpeg", "png"};
        for (String extension : imageExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    private void uploadImageToFirebase(File file) {
        Uri fileUri = Uri.fromFile(file);
        StorageReference fileReference = storageReference.child("images/" + file.getName());

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        //Toast.makeText(LoadingScreen.this, "분류 완료되었습니다.", Toast.LENGTH_LONG).show();

        UploadTask uploadTask = fileReference.putFile(fileUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            //Log.d(TAG, "Image uploaded successfully: " + file.getName());
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            //Log.e(TAG, "Failed to upload image: " + file.getName(), e);
        }).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
        });
    }
}


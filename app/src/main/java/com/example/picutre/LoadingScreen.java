package com.example.picutre;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadingScreen extends AppCompatActivity {

    private Button btn_next, btn_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading_screen);

        btn_next = findViewById(R.id.btn_next);
        btn_test = findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_test.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                //Toast.makeText(LoadingScreen.this, "분류 완료되었습니다.", Toast.LENGTH_LONG).show();
                showDialogAutomatically(); // 다이얼로그 자동으로 띄우는 메소드
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

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
    }
}


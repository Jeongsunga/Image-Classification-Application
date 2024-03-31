package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Filter extends AppCompatActivity {

    Button btn_next;
    CheckBox chbox_locate;
    CheckBox chbox_eyeclosed;
    CheckBox chbox_faceOpen;
    CheckBox chbox_faceClosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);

        btn_next = findViewById(R.id.btn_next);
        chbox_locate = findViewById(R.id.chbox_locate);
        chbox_eyeclosed = findViewById(R.id.chbox_eyeclosed);
        chbox_faceOpen = findViewById(R.id.chbox_faceOpen);
        chbox_faceClosed = findViewById(R.id.chbox_faceClosed);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //분류 필터를 한 가지도 선택하지 않았을 때 토스트 문구 알림
                if(chbox_faceOpen.isChecked() == false && chbox_eyeclosed.isChecked() == false &&
                chbox_faceClosed.isChecked() == false && chbox_locate.isChecked() == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "필터를 최소 한 가지 선택해 주세요.",Toast.LENGTH_SHORT);
                    toast.show();
                }
                //필터를 한가지라도 선택했을 때 다음 화면으로 넘어갈 수 있도록 함
                if (chbox_faceOpen.isChecked() == true || chbox_eyeclosed.isChecked() == true ||
                        chbox_faceClosed.isChecked() == true || chbox_locate.isChecked() == true) {
                    Intent intent = new Intent(Filter.this, GalleryList.class);
                    startActivity(intent);
                }

            }
        });

        //위치에 따른 사진 분류
        chbox_locate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 실행할 코드 여기에다가 적기
            }
        });
        
        //얼굴이 보이는 사진
        chbox_faceOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //실행할 코드 작성
            }
        });

        //눈 감은 사진
        chbox_eyeclosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 만약에 눈 감은 사진은 체크하고 얼굴이 보이는 사진을 체크하지 않았다면, 자동으로 체크되게 함
                if (chbox_faceOpen.isChecked() == false) {
                    chbox_faceOpen.setChecked(true);
                    // 이후에 실행할 코드 작성
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
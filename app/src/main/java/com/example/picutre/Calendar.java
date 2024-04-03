package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calendar extends AppCompatActivity {

    private Button btn_next;
    private EditText edt_oneday, edt_startday, edt_endDay;
    private RadioGroup radioGroup;
    private RadioButton radio_oneDay, radio_during;
    private TextView wave, warring, warring2;
    private String oneDay, startDay, endDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);

        edt_oneday = findViewById(R.id.edt_oneday);
        edt_startday = findViewById(R.id.edt_startday);
        edt_endDay = findViewById(R.id.edt_endDay);
        radioGroup = findViewById(R.id.radioGroup);     //라디오 버튼을 담고 있는 그룹
        btn_next = findViewById(R.id.btn_next);
        radio_oneDay = findViewById(R.id.radio_oneDay);
        radio_during = findViewById(R.id.radio_during);
        wave = findViewById(R.id.text_wave);
        warring = findViewById(R.id.warring);
        warring2 = findViewById(R.id.warring2);


        //라디오 버튼의 상태값이 변경됐음을 감지
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_oneDay) {
                    // 하루 일정만 텍스트뷰에 띄움
                    edt_oneday.setVisibility(View.VISIBLE);
                    edt_oneday.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            warring2.setText("날짜를 모두 입력해주세요.");
                            warring2.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(s.length() != 10) {
                                warring2.setText("날짜를 모두 입력해주세요.");
                                warring2.setVisibility(View.VISIBLE);
                            }else {
                                warring2.setVisibility(View.INVISIBLE);

                            }
                        }
                    });

                }else {
                    edt_oneday.setVisibility(View.INVISIBLE);
                    warring2.setVisibility(View.INVISIBLE);
                }

                if(checkedId == R.id.radio_during) {
                    // 이틀 일정을 텍스트뷰에 띄움
                    edt_startday.setVisibility(View.VISIBLE);
                    edt_endDay.setVisibility(View.VISIBLE);
                    wave.setVisibility(View.VISIBLE);

                }else {
                    edt_startday.setVisibility(View.INVISIBLE);
                    edt_endDay.setVisibility(View.INVISIBLE);
                    wave.setVisibility(View.INVISIBLE);
                    warring.setVisibility(View.INVISIBLE);
                }

                edt_startday.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //텍스트 변경 전 호출되는 메소드
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //텍스트 변경 될 때 호출되는 메소드
                        warring.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //텍스트가 변경 후에 호출되는 메소드
                        if (s.length() == 10) {
                            startDay = edt_startday.getText().toString().trim();
                            String[] parts1 = startDay.split("\\.");
                            // 달이 1-12월 벗어나면 에러, 날이 벗어나면 에러
                            int startYear = Integer.parseInt(parts1[0]);
                            int startMonth = Integer.parseInt(parts1[1]);
                            int Start_Day = Integer.parseInt(parts1[2]);

                            edt_endDay.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    warring.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (s.length() == 10) {
                                        endDay = edt_endDay.getText().toString().trim();
                                        String[] parts2 = endDay.split("\\.");
                                        int EndYear = Integer.parseInt(parts2[0]);
                                        int EndMonth = Integer.parseInt(parts2[1]);
                                        int End_Day = Integer.parseInt(parts2[2]);

                                        // 날짜가 같거나 뒤의 날짜가 앞의 날짜보다 이를 때 에러 문구 띄우기
                                        if(startYear == EndYear && startMonth == EndMonth && Start_Day == End_Day) {
                                            //날짜가 같습니다.
                                            warring.setText("날짜가 같습니다.");
                                            warring.setVisibility(View.VISIBLE);
                                        }else if(startYear > EndYear) { // 시작 년도가 끝 년도보다 클 때
                                            warring.setText("날짜 입력이 잘못되었습니다.");
                                            warring.setVisibility(View.VISIBLE);
                                        }else if(startYear == EndYear && startMonth > EndMonth) { // 시작 년도와 끝 년도가 같고 시작 년도가 끝 년도보다 클 때
                                            warring.setText("날짜 입력이 잘못되었습니다.");
                                            warring.setVisibility(View.VISIBLE);
                                        }else if(startYear == EndYear && startMonth == EndMonth && Start_Day > End_Day) {
                                            // // 시작 년도와 끝 년도가 같고, 시작 년도와 끝 년도가 같고, 시작 날이 끝 날 보다 클 때
                                            warring.setText("날짜 입력이 잘못되었습니다.");
                                            warring.setVisibility(View.VISIBLE);
                                        }

                                    }else {
                                        warring.setText("날짜를 모두 입력해주세요.");
                                        warring.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                        }else {
                            warring.setText("날짜를 모두 입력해주세요.");
                            warring.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        int warring_sight = warring.getVisibility();
        int warring2_sight = warring2.getVisibility();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //한 가지도 선택하지 않았을 때 토스트 문구 알림
                if(radio_oneDay.isChecked() == false && radio_during.isChecked() == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "한 가지 선택해 주세요.",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(warring_sight == View.VISIBLE || warring2_sight == View.VISIBLE) {
                    Toast toast = Toast.makeText(getApplicationContext(), "날짜를 정확하게 입력해주세요 .",Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    Intent intent = new Intent(Calendar.this, GalleryList.class);
                    startActivity(intent);
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
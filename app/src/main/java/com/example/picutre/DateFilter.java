package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFilter extends AppCompatActivity {

    private Button btn_next;
    private Button btn_oneday, btn_twoday;
    private TextView tv_date;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datefilter);

       btn_next = findViewById(R.id.btn_next);
        tv_date = findViewById(R.id.tv_date);
        btn_oneday = findViewById(R.id.btn_oneday);
        btn_twoday = findViewById(R.id.btn_twoday);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        //오늘 날짜
        Long today = MaterialDatePicker.todayInUtcMilliseconds();

        //날짜 선택 버튼
        btn_oneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Date Picker")
                        .setSelection(today).build(); // 오늘 날짜 셋팅

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.setTimeInMillis(selection);
                        Calendar currentCalendar = Calendar.getInstance();

                        // 미래 날짜는 선택 불가 하도록 함
                        if(selectedCalendar.after(currentCalendar)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "미래의 날짜는 선택 불가합니다.\n다시 선택해 주세요.",Toast.LENGTH_SHORT);
                            toast.show();
                            MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                                    .setTitleText("Date Picker")
                                    .setSelection(today).build(); // 오늘 날짜 셋팅
                            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                            // 당일 까지 선택 가능
                        }else if (selectedCalendar.before(currentCalendar)){
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                            Date date = new Date();
                            date.setTime(selection);

                            String dateString = simpleDateFormat.format(date);

                            tv_date.setText(dateString);
                        }
                    }
                });
            }
        });

        //기간 선택 버튼
        btn_twoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Date Picker").build();

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener
                        (new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                            @Override
                            public void onPositiveButtonClick(Pair<Long, Long> selection) {

                                Calendar selectedCalendar1 = Calendar.getInstance();
                                selectedCalendar1.setTimeInMillis(selection.first);
                                Calendar selectedCalendar2 = Calendar.getInstance();
                                selectedCalendar2.setTimeInMillis(selection.second);
                                Calendar currentCalendar = Calendar.getInstance();

                                if(selectedCalendar1.equals(selectedCalendar2) ) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "같은 날짜는 선택 불가합니다.\n다시 선택해 주세요.",Toast.LENGTH_SHORT);
                                    toast.show();

                                    MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                                            .setTitleText("Date Picker").build();

                                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                                }else if(selectedCalendar1.after(currentCalendar) || selectedCalendar2.after(currentCalendar)) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "미래 날짜는 선택 불가합니다.\n다시 선택해 주세요.",Toast.LENGTH_SHORT);
                                    toast.show();

                                    MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                                            .setTitleText("Date Picker").build();

                                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                                }

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

                                Date date1 = new Date();
                                Date date2 = new Date();

                                date1.setTime(selection.first);
                                date2.setTime(selection.second);

                                String dateString1 = simpleDateFormat.format(date1);
                                String dateString2 = simpleDateFormat.format(date2);

                                tv_date.setText("시작일 : " + dateString1 + "\n" + "마감일 : " + dateString2);
                            }
                        });
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_date.getText().toString();
                if(text.equals("날짜")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.",Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    Intent intent = new Intent(DateFilter.this, GalleryList.class);
                    startActivity(intent);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v,insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
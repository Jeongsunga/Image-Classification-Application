package com.example.picutre;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

public class GalleryList extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    File file2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    String[] folders2 = file2.list();

    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    String[] folders = file.list();

    private ListView listview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_list);


        listview = findViewById(R.id.listview);

        List<String> data1 = new ArrayList<>();


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data1);

        listview.setAdapter(adapter1);

        for(int i =0; i< folders.length; i++) {
            if(folders[i].startsWith(".") || folders[i].isEmpty()) continue;
            else {
                data1.add(folders[i]);
                adapter1.notifyDataSetChanged();
            }
        }

        for(int i=0; i< folders2.length; i++) {
            if(folders2[i].isEmpty() || folders2[i].startsWith(".")) continue;
            else {
                data1.add(folders2[i]);
                adapter1.notifyDataSetChanged();
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 로딩 화면으로 넘어감
                Intent intent = new Intent(GalleryList.this, LoadingScreen.class);
                startActivity(intent);
            }
        });

/*
        ArrayList<Data> list = new ArrayList<>();
        for(int i = 0; i<folders.length; i++) {
            list.add(new Data(folders[i], i));
        }
        for(int i=0; i<folders2.length; i++) {
            list.add(new Data(folders2[i], i));
        }

        class listAdapter extends BaseAdapter {
            List<Data> lists;

            public listAdapter(List<Data> lists) {
                this.lists = lists;
            }

            @Override
            public int getCount() {
                return lists.size();
            }
            public Object getItem(int i) {
                return lists.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, ViewGroup viewGroup) {
                return null;
            }
        }
        */



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}
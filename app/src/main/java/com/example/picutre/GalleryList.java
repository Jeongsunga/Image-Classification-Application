package com.example.picutre;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GalleryList extends AppCompatActivity {

/*
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;


    File file2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    String[] folders2 = file2.list();
    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    String[] folders = file.list();
    ListView listview;
    */

    private static final int REQUEST_PERMISSION = 100;
    private RecyclerView recyclerView;
    private listAdaptor listAdaptor;
    private List<String> folderNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_list);
/*
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
*/

        recyclerView = findViewById(R.id.recylcerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        folderNames = new ArrayList<>();
        listAdaptor = new listAdaptor(folderNames);
        recyclerView.setAdapter(listAdaptor);

        loadGalleryFolders();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadGalleryFolders() {
        HashSet<String> folderSet = new HashSet<>();
        String[] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        if (cursor != null) {
            int folderNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String folderName = cursor.getString(folderNameIndex);
                folderSet.add(folderName);
            }
            cursor.close();
        }

        folderNames.clear();
        folderNames.addAll(folderSet);
        listAdaptor.notifyDataSetChanged();
    }

}
package com.example.picutre;

import android.os.Bundle;
import android.os.Environment;
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
        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);

        for(int i =0; i< folders.length; i++) {
            if(folders[i].startsWith(".") || folders[i].isEmpty() == true) {
                continue;
            }else {
                data.add(folders[i]);
                adapter.notifyDataSetChanged();
            }
        }

        for(int i=0; i< folders2.length; i++) {
            if(folders2[i].isEmpty() == true || folders2[i].startsWith(".")) {
                continue;
            }else {
                data.add(folders2[i]);
                adapter.notifyDataSetChanged();
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
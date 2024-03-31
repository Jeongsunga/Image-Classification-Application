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
    String pictureFolderPath = "/storage/pictures/";
    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    //File file2 = new File(pictureFolderPath);
    String[] folders = file.list();
    //String[] folders1 = file2.list();
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
            if(folders[i].startsWith(".")) {
                continue;
            }else {
                data.add(folders[i]);
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
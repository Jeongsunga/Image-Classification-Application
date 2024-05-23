package com.example.picutre;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GalleryList extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 100;
    private RecyclerView recyclerView;
    private listAdaptor listAdaptor;
    private List<String> folderNames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery_list);

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
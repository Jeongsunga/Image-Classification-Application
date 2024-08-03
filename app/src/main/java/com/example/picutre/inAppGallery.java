package com.example.picutre;
// 파이어베이스 스토리지에 있는 폴더들을 보여주는 클래스

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


import java.util.Random;

public class inAppGallery extends AppCompatActivity {



    private RecyclerView recyclerView;
    private StorageAdaptor storageAdaptor;
    private List<StorageItem> storageItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_app_gallery);

        recyclerView = findViewById(R.id.recylcerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storageItemList = new ArrayList<>();
        storageAdaptor = new StorageAdaptor(storageItemList);
        recyclerView.setAdapter(storageAdaptor);

        fetchStorageItems();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void fetchStorageItems() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix : listResult.getPrefixes()) {
                    fetchFolderDetails(prefix);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(inAppGallery.this, "Failed to fetch folders", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Failed to fetch folders", e);
            }
        });
    }

    private void fetchFolderDetails(StorageReference folderRef) {
        folderRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                String folderName = folderRef.getName();
                //String firstImagePath = null;
                int count = listResult.getItems().size();

                if (!listResult.getItems().isEmpty()) {
                    StorageReference firstImageRef = listResult.getItems().get(0);
                    //firstImagePath = listResult.getItems().get(0).getPath();
                    firstImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String firstImagePath = uri.toString();
                            StorageItem storageItem = new StorageItem(folderName, firstImagePath, count);
                            storageItemList.add(storageItem);
                            storageAdaptor.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("MainActivity", "Failed to get download URL", e);
                        }
                    });
                } else {
                    StorageItem storageItem = new StorageItem(folderName, null, count);
                    storageItemList.add(storageItem);
                    storageAdaptor.notifyDataSetChanged();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("MainActivity", "Failed to fetch folder details", e);
            }
        });
    }


}
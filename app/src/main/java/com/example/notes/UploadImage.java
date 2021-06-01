package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class UploadImage extends AppCompatActivity {
    private static final int RQCODE_SELECT_IMAGE = 1;
    private static final int REQ_CODE_PERMISSION = 2;
    private Button btn_sel_image;
    private StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        if(ContextCompat.checkSelfPermission(this,( Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED)
        {
            String permission [] = new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(this,permission,REQ_CODE_PERMISSION);
        }
        btn_sel_image = findViewById(R.id.upload_select_image);

        reference = FirebaseStorage.getInstance().getReference();
        btn_sel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,RQCODE_SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RQCODE_SELECT_IMAGE && resultCode == RESULT_OK)
        {
         Uri uriImage = data.getData();
            StorageReference storageReference = reference.child(Calendar.getInstance().getTime().toString());
            if(uriImage != null) {
                storageReference.putFile(uriImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(UploadImage.this, "successful", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        {
            if(requestCode == REQ_CODE_PERMISSION )
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "11111", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
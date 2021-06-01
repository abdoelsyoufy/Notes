package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {
    Context context ;
    Activity activity ;
  private   FirebaseAuth firebaseAuth=null;
    EditText ed_pass,ed_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
         firebaseAuth = FirebaseAuth.getInstance();
        ed_pass =findViewById(R.id.auth_ed_password);
        ed_mail =findViewById(R.id.auth_ed_email);
        Button btn_reg = findViewById(R.id.auth_btn_login);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if(!ed_mail.getText().toString().isEmpty()&&!ed_pass.getText().toString().isEmpty()) {
                    String email = ed_mail.getText().toString();
                    String password = ed_pass.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful())
                                        Toast.makeText(Authentication.this, "Successful", Toast.LENGTH_LONG).show();
                                     else
                                        Toast.makeText(Authentication.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                //}

            }
        });


    }

}
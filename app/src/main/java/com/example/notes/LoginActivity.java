package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
EditText pass ,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pass =findViewById(R.id.login_ed_password);
        mail = findViewById(R.id.login_ed_email);
        Button btn_login= findViewById(R.id.login_btn_login);
        auth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(mail.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(getBaseContext(),SignUp.class);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        });

    }
}
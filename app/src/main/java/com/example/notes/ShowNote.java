package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowNote extends AppCompatActivity {
TextView tv_title,tv_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        tv_title = findViewById(R.id.shownotetv_title);
        tv_note = findViewById(R.id.shownotetv_note);

        Intent intent =getIntent();
        tv_title.setText(intent.getStringExtra(MainActivity.getTitleKeyNote()));
        tv_note.setText(intent.getStringExtra(MainActivity.getNoteKeyNote()));

    }
}
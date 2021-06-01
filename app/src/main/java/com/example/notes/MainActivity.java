package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TITLE_KEY_NOTE ="title_note" ;
    private static final String NOTE_KEY_NOTE ="note" ;
    NoteAdapter adapter;

    public static String getTitleKeyNote() {
        return TITLE_KEY_NOTE;
    }

    public static String getNoteKeyNote() {
        return NOTE_KEY_NOTE;
    }

    FloatingActionButton actionButton;
    DatabaseReference myRef;
    ArrayList<Note> notes;
    AlertDialog alertDialog;
    ListView listView;
    Note noteselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Notes");
        notes = new ArrayList<>();
listView =findViewById(R.id.note_lv);
         actionButton = findViewById(R.id.note_fab);
         actionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                showDialog();
             }
         });

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(MainActivity.this,ShowNote.class);
                 Note note = notes.get(position);
                 intent.putExtra(TITLE_KEY_NOTE,note.getTitle());
                 intent.putExtra(NOTE_KEY_NOTE,note.getNote());
                 startActivity(intent);


             }
         });



         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                  noteselected = notes.get(position);
                  registerForContextMenu(listView);
                 return false;
             }
         });


    }

    public  void  showDialog()
    {
        AlertDialog.Builder dialog = new  AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.note_custome_dailog,null,false);
        dialog.setView(view);
         alertDialog= dialog.create();
        alertDialog.show();
        setNoteInFirebase(view);

    }

    public  void setNoteInFirebase(View view)
    {

        EditText ed_title = view.findViewById(R.id.custom_dialog_ed_title);
        EditText ed_node = view.findViewById(R.id.custom_dialog_ed_note);
        Button btn_save =view.findViewById(R.id.custom_dialog_btn);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String title = ed_title.getText().toString();
                String node = ed_node.getText().toString();

                if(title.isEmpty() && node.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "empty", Toast.LENGTH_LONG).show();

                }

                else
                {
                    String id =myRef.push().getKey();
                    Note newnode = new Note(id,title,node, getdate());
                    myRef.child(id).setValue(newnode);
                    alertDialog.dismiss();

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                   Log.d("message33",childDataSnapshot.getValue(Note.class).getTitle());
                    Note note = childDataSnapshot.getValue(Note.class);
                    notes.add(0,note);
                }

                adapter = new NoteAdapter(MainActivity.this,notes);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //Log.d("abcd","title  ");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public  String getdate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat =new SimpleDateFormat("EEEE hh:mm a");
        String date =dateFormat.format(calendar.getTime());
        return date;



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.sort_notes_ByTitle:
               adapter.sortNotesByTitle();
                adapter.notifyDataSetChanged();
                break;
            case  R.id.sort_notes_ByDate:
                adapter.sortNotesByDate();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String key = noteselected.getId();
        switch (item.getItemId())
        {
            case R.id.edit:
            case R.id.delete:
                myRef.child(key).removeValue();
                break;

        }
        return super.onContextItemSelected(item);
    }

}
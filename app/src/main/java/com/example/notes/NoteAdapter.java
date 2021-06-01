package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.snapshot.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class NoteAdapter extends BaseAdapter {
   private Context context;
   private ArrayList<Note>  notes;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_custom_listview,
                parent,false);

        TextView tv_title = v.findViewById(R.id.custom_listview_title);
        TextView tv_date = v.findViewById(R.id.custom_listview_date);
        Note note =getItem(position);
        tv_title.setText(note.getTitle());
        tv_date.setText((CharSequence) note.getNotedate());
        return v;
    }
    public  void   sortNotesByTitle()
    {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public  void   sortNotesByDate()
    {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                return lhs.getNotedate().compareTo(rhs.getNotedate());
            }
        });
    }
}

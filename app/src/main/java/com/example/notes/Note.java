package com.example.notes;

import java.util.Date;
import java.util.Map;

public class Note {
    private String title ,note,id;
    private String notedate;
public Note(){}
    public Note(String id, String title, String note ,String notedate) {
        this.title = title;
        this.note = note;
        this.id = id;
        this.notedate = notedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNotedate() {
        return notedate;
    }

    public void setNotedate(String notedate) {
        this.notedate = notedate;
    }
}

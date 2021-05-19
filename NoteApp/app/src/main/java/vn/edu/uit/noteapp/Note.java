package vn.edu.uit.noteapp;

public class Note {
    private long ID;
    private String TITLE;
    private  String NOTE_CONTENT;
    private String DATE;
    private String TIME;
    Note(){}
    Note(String title, String content, String date, String time ){
        this.TITLE=title;
        this.NOTE_CONTENT=content;
        this.DATE=date;
        this.TIME=time;
    }
    Note(long id, String title, String content, String date, String time ){
        this.ID=id;
        this.TITLE=title;
        this.NOTE_CONTENT=content;
        this.DATE=date;
        this.TIME=time;
    }
}

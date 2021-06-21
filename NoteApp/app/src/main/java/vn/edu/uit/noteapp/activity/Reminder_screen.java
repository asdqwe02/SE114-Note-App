package vn.edu.uit.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Reminder_adapter;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.entities.Note;
import vn.edu.uit.noteapp.adapter.NoteAdapter;
import vn.edu.uit.noteapp.listeners.NotesListener;
import vn.edu.uit.noteapp.util.MyItemTouchHelper;

public class Reminder_screen extends AppCompatActivity implements NotesListener{
    ArrayList<Note> notesList;
    NoteAdapter noteAdapter;
    RecyclerView recyclerView;
    boolean refresh;
    private int noteClickedPosition = -1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;

    public Reminder_screen() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reminders");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RecyclerView_reminder);
        notesList = new ArrayList<Note>() {};

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(notesList,  this, 2,this);
        //2 là biến để biết reminder screen đang dùng note adapter

        /**/
//        ItemTouchHelper.Callback callback = new MyItemTouchHelper(noteAdapter);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//        noteAdapter.setTouchHelper(itemTouchHelper);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        //
        recyclerView.setAdapter(noteAdapter);

        getNotes(REQUEST_CODE_SHOW_NOTES, false);

    }


    @SuppressLint("StaticFileLeak")
    public void getNotes(final int requestCode, final boolean isNoteDeleted) {
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getApplicationContext())
                        .noteDao().getReminderNote();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("MY_NOTE", notes.toString());
                if (requestCode == REQUEST_CODE_SHOW_NOTES) {
                    notesList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    notesList.remove(noteClickedPosition);
                    if (isNoteDeleted) {
                        noteAdapter.notifyItemRemoved(noteClickedPosition);
                    } else {
                        notesList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        noteAdapter.notifyItemChanged(noteClickedPosition);
                    }

                }

            }
        }
        new GetNoteTask().execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null)
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            refresh=false;
        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), Note_screen.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }

    /**/
    public int getId(int position){
        return notesList.get(position).getId();
    }

    protected void onPause() {
        super.onPause();
        refresh =true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh){
            notesList.clear();
            noteAdapter = new NoteAdapter(notesList, this, 0,this);
            recyclerView.setAdapter(noteAdapter);
            getNotes(REQUEST_CODE_SHOW_NOTES, false);
            noteAdapter.notifyDataSetChanged();
        }

    }
}
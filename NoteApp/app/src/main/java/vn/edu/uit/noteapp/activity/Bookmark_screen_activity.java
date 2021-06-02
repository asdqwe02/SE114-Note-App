package vn.edu.uit.noteapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.NotesDatabase;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.BookmarkScreen_adapter;
import vn.edu.uit.noteapp.adapter.NoteAdapter;
import vn.edu.uit.noteapp.data.Data_model_bookmark;
import vn.edu.uit.noteapp.listeners.NotesListener;

public class Bookmark_screen_activity extends AppCompatActivity implements NotesListener {
    ArrayList<Note> notelist;
    BookmarkScreen_adapter bookmarkAdapter;
    NoteAdapter note_adapter;
    RecyclerView recyclerView;

    private int noteClickedPosition = -1;
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_screen);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Bookmarks");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
//        Data = new ArrayList<>();
//        CreateBookmark();
        notelist = new ArrayList<>();
        note_adapter = new NoteAdapter(notelist, (NotesListener) this);
        recyclerView.setAdapter(note_adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        getNotes(REQUEST_CODE_SHOW_NOTES, false);
    }
    @SuppressLint("StaticFileLeak")
    public void getNotes(final int requestCode, final boolean isNoteDeleted) {
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getApplicationContext())
                        .noteDao().getBookmarkNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("MY_NOTE", notes.toString());
                if (requestCode == REQUEST_CODE_SHOW_NOTES) {
                    notelist.addAll(notes);
                    note_adapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    notelist.add(0, notes.get(0));
                    note_adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    notelist.remove(noteClickedPosition);
                    if (isNoteDeleted){
                        note_adapter.notifyItemRemoved(noteClickedPosition);
                    } else{
                        notelist.add(noteClickedPosition, notes.get(noteClickedPosition));
                        note_adapter.notifyItemChanged(noteClickedPosition);

                    }

                }

            }
        }
        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null)
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted",false));
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
    //Create some bookmark
//    public void CreateBookmark()
//    {
//        Data.add(new Data_model_bookmark("Work"));
//        Data.add(new Data_model_bookmark("Personal"));
//        Data.add(new Data_model_bookmark("Study"));
//    }
}
package vn.edu.uit.noteapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.NoteDao;
import vn.edu.uit.noteapp.NotesDatabase;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.NoteAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private ImageButton fab_add_note;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton img_menuBTN;

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_add_note = (ImageButton) findViewById(R.id.img_addnote);
        fab_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getApplicationContext(), Note_screen.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });

        /*------------Hooks------------*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        img_menuBTN = (ImageButton) findViewById(R.id.imgbtn_menu);

        img_menuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.bringToFront();
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        //Make menu clickable
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //Note RecyclerView
        noteRecyclerView = findViewById(R.id.NotesRecyclerView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        noteRecyclerView.setAdapter(noteAdapter);

        getNotes();
    }

    @SuppressLint("StaticFileLeak")
    public void getNotes() {
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("MY_NOTE", notes.toString());
                if (noteList.size() == 0) {
                    noteList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                } else {
                    noteList.add(0, notes.get(0));
                    noteAdapter.notifyItemInserted(0);
                }
                noteRecyclerView.setOverScrollMode(0);

            }
        }
        new GetNoteTask().execute();
    }


    @Override
    //This method is used to avoid the app crash if we hit the back button
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            //Link notes screen (home screen)
            case R.id.nav_home:
                break; //because we are already in note screen

            //link to bookmark screen
            case R.id.nav_bookmark:
                //Intent intent = new Intent(MainActivity.this, Bookmark_screen.class);
                //startActivity(intent);
                break;

            //link to notebook screen
            case R.id.nav_notebook:
                Intent intent = new Intent(MainActivity.this, Notebook_Screen.class);
                startActivity(intent);
                break;

            //link to reminder screen
            case R.id.nav_reminder:
                //Intent intent = new Intent(MainActivity.this, Reminder_screen.class);
                //startActivity(intent);
                break;

            //link to settings screen
            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, Setting_Screen.class);
                startActivity(intent);
                break;

            //link to feedback screen
            case R.id.nav_feedback:
                intent = new Intent(MainActivity.this, Feedback_activity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
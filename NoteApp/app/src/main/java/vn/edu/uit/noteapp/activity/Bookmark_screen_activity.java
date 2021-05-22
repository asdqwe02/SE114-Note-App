package vn.edu.uit.noteapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.BookmarkScreen_adapter;
import vn.edu.uit.noteapp.data.Data_model_bookmark;

public class Bookmark_screen_activity extends AppCompatActivity {
    ArrayList<Data_model_bookmark> Data;
    BookmarkScreen_adapter bookmarkAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_screen);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Bookmarks");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        Data = new ArrayList<>();

        CreateBookmark();
        bookmarkAdapter = new BookmarkScreen_adapter(this,Data);
        recyclerView.setAdapter(bookmarkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Create some bookmark
    public void CreateBookmark()
    {
        Data.add(new Data_model_bookmark("Work"));
        Data.add(new Data_model_bookmark("Personal"));
        Data.add(new Data_model_bookmark("Study"));
    }
}
package vn.edu.uit.noteapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.noteapp.adapter.Model_Item;
import vn.edu.uit.noteapp.adapter.Recycler_Adapter;

public class NoteBookScreenn extends AppCompatActivity {
    ArrayList<Model_Item> item_model;
    Recycler_Adapter recycler_adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call ActionBar
        ActionBar ab = getSupportActionBar();
        //Title for ActioneBar
        ab.setTitle("Notebook Screen");
        //Background ActionBar
        ab.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //Button Back ActionBar
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setSubtitle("This is Subtitle");
        setContentView(R.layout.activity_note_book_screenn);
        //connect adapter
        recyclerView = findViewById(R.id.recycle_main);
        item_model = new ArrayList<>();
        CreateItem();
        recycler_adapter = new Recycler_Adapter(this, item_model);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void CreateItem()
    {
        item_model.add(new Model_Item("new"));
        item_model.add(new Model_Item("test"));


    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
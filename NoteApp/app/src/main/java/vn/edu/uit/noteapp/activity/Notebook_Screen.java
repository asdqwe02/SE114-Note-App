package vn.edu.uit.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.adapter.Notebookscreen_recyclerview_adapter;

public class Notebook_Screen extends AppCompatActivity {
    ArrayList<Model_Item_Notebook_screen> item_model;
    Notebookscreen_recyclerview_adapter recycler_adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        //Title for ActionBar
        ab.setTitle("Notebook Screen");
        //Background ActionBar
        ab.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //Button Back ActionBar
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setSubtitle("This is Subtitle");
        setContentView(R.layout.activity_notebook_screen);
        recyclerView = findViewById(R.id.recycle_main);
        item_model = new ArrayList<>();
        CreateItem();
        recycler_adapter = new Notebookscreen_recyclerview_adapter(this, item_model);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void CreateItem()
    {
        item_model.add(new Model_Item_Notebook_screen("new"));
        item_model.add(new Model_Item_Notebook_screen("test"));
        item_model.add(new Model_Item_Notebook_screen("Work"));
        item_model.add(new Model_Item_Notebook_screen("Study"));
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
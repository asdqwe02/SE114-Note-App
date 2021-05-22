package vn.edu.uit.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.adapter.Notebookscreen_recyclerview_adapter;

public class Notebook_Screen extends AppCompatActivity {
    ArrayList<Model_Item_Notebook_screen> item_model;
    Notebookscreen_recyclerview_adapter recycler_adapter;
    RecyclerView recyclerView;
    ImageButton addbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_screen);
        ActionBar ab = getSupportActionBar();
        //Title for ActionBar
        ab.setTitle("Notebook Screen");
        //Background ActionBar
        ab.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //Button Back ActionBar
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setSubtitle("This is Subtitle");
        //add item into recyclerview
        addbutton = findViewById(R.id.img_addnotebook);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Notebook_Screen.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                final View custom_layout = getLayoutInflater().inflate(R.layout.add_notebook_dialog,null);
                dialog.setView(custom_layout);
                dialog.setTitle("Add Notebook");
                dialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edit = custom_layout.findViewById(R.id.notebook_name_create);
                        item_model.add(new Model_Item_Notebook_screen(edit.getText().toString()));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog done = dialog.create();
                done.show();
            }
        });
        recyclerView = findViewById(R.id.recycle_main);
        item_model = new ArrayList<>();
//        CreateItem();
        recycler_adapter = new Notebookscreen_recyclerview_adapter(this, item_model);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
//    public void CreateItem()
//    {
//        item_model.add(new Model_Item_Notebook_screen("new"));
//        item_model.add(new Model_Item_Notebook_screen("test"));
//        item_model.add(new Model_Item_Notebook_screen("Work"));
//        item_model.add(new Model_Item_Notebook_screen("Study"));
//    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
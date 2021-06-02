package vn.edu.uit.noteapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Reminder_adapter;
import vn.edu.uit.noteapp.data.Reminder_item;

public class Reminder_screen extends AppCompatActivity {

    ArrayList<Reminder_item> items;
    Reminder_adapter reminder_adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reminders");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RecyclerView_reminder);
        items = new ArrayList<>();
        CreateItem();
        reminder_adapter = new Reminder_adapter(this, items);
        recyclerView.setAdapter(reminder_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void CreateItem()
    {
        items.add(new Reminder_item("new","21/02/2021","10 A.M"));
        items.add(new Reminder_item("work","23/02/2021","10 A.M"));
        items.add(new Reminder_item("new","21/04/2021","10 A.M"));
    }
}
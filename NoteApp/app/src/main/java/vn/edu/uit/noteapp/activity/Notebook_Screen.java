package vn.edu.uit.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
        ab.setTitle("Notebooks");
        //Background ActionBar
        ab.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Button Back ActionBar
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setSubtitle("This is Subtitle");
        //add item into recyclerview
        addbutton = findViewById(R.id.img_addnotebook);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog;
                dialog = new Dialog(Notebook_Screen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_notebook_dialog);

                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.BOTTOM;
                window.setAttributes(windowAttributes);

                dialog.setCancelable(true);

                EditText editName = dialog.findViewById(R.id.notebook_name_create);
                Button btn_Cancel = dialog.findViewById(R.id.btn_cancel);
                Button btn_Create = dialog.findViewById(R.id.btn_create);

                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_Create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editName.getText().toString();
                        if (name.matches("")){
                            Toast.makeText(Notebook_Screen.this, "Please enter your notebook name", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            item_model.add(new Model_Item_Notebook_screen(editName.getText().toString()));
                            dialog.dismiss();
                            Toast.makeText(Notebook_Screen.this, "Create success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
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
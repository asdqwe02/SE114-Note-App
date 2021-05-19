package vn.edu.uit.noteapp.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import vn.edu.uit.noteapp.adapter.Checkbox_recyclerview_adapter;
import vn.edu.uit.noteapp.Checkbox_recyclerview_items;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;
import vn.edu.uit.noteapp.R;

public class Note_screen extends AppCompatActivity implements Note_Screen_Bottom_Sheet_Setting.BottomSheetListener {

    private String note_screen_color;

    private static final String TITLE_FILE_NAME = "exampleTITLE.txt";
    private static final String NOTE_FILE_NAME = "exampleNOTE.txt";

    //shared preferences
    public static final String SHARE_PREFS = "sharedPrefs";
    public static final String NOTE_SCREEN_COLOR = "note_screen_color";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText title_Text;
    EditText note_Text;
    ImageButton show_CheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_screen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        title_Text=findViewById(R.id.titleText);
        note_Text=findViewById(R.id.noteText);
        show_CheckBox=findViewById(R.id.show_checkbox);

        show_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note_Text.getVisibility()==View.VISIBLE && mRecyclerView.getVisibility()==View.INVISIBLE){
                    note_Text.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    note_Text.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }

            }
        });

        //handling checkbox recyclerview
        ArrayList<Checkbox_recyclerview_items> exampleList= new ArrayList<>();
        exampleList.add(new Checkbox_recyclerview_items("text 1",false));
        exampleList.add(new Checkbox_recyclerview_items("text 2",false));
        exampleList.add(new Checkbox_recyclerview_items("text 3",false));
        exampleList.add(new Checkbox_recyclerview_items("text 4",false));

        mRecyclerView = findViewById(R.id.checkbox_recyclerview_layout);
        mRecyclerView.setHasFixedSize(true); // if recycler view don't change set this to true note: will delete later
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Checkbox_recyclerview_adapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);


        //share preferences and save txt file
        loadData();
        updateData();
    }
    public void Open_Bottom_Sheet_Setting()
    {
        Note_Screen_Bottom_Sheet_Setting bottomSheet = new Note_Screen_Bottom_Sheet_Setting();
        bottomSheet.show(getSupportFragmentManager(), "Note_Screen_bottomSheetSetting");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.open_note_bottom_sheet, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Open_Bottom_Sheet_Setting:
                Open_Bottom_Sheet_Setting();
                return true;
            case android.R.id.home:
                saveData();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void saveData()
    {
        View view = this.getWindow().getDecorView();
        Drawable background = view.getBackground();
        int colorID = ((ColorDrawable) background).getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorID));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NOTE_SCREEN_COLOR,hexColor);
        editor.apply();
        saveNote();
    }
    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        note_screen_color = sharedPreferences.getString(NOTE_SCREEN_COLOR,"#FFFFFF");
    }
    public void updateData()
    {
        loadNote();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(note_screen_color));
    }
    public void saveNote()
    {
        String noteTitle=title_Text.getText().toString();
        String noteText=note_Text.getText().toString();

        FileOutputStream title_fos = null;
        FileOutputStream note_fos = null;

        try {
            title_fos=openFileOutput(TITLE_FILE_NAME,MODE_PRIVATE);
            note_fos=openFileOutput(NOTE_FILE_NAME,MODE_PRIVATE);

            title_fos.write(noteTitle.getBytes());
            note_fos.write(noteText.getBytes());

            title_Text.getText().clear();
            note_Text.getText().clear();

            Toast.makeText(this, "saved to" + getFilesDir() + "/" + TITLE_FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (title_fos!=null || note_fos!=null) {
                try {
                    title_fos.close();
                    note_fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void loadNote()
    {
        FileInputStream title_fis = null;
        FileInputStream note_fis = null;

        try {
            String noteTitle;
            String noteText;

            title_fis = openFileInput(TITLE_FILE_NAME);
            note_fis = openFileInput(NOTE_FILE_NAME);

            InputStreamReader title_isr = new InputStreamReader(title_fis);
            BufferedReader title_br = new BufferedReader(title_isr);
            StringBuilder title_sb = new StringBuilder();

            InputStreamReader note_isr = new InputStreamReader(note_fis);
            BufferedReader note_br = new BufferedReader(note_isr);
            StringBuilder note_sb = new StringBuilder();

            while ((noteTitle = title_br.readLine())!=null){
                title_sb.append(noteTitle);
            }
            while((noteText = note_br.readLine())!=null){
                note_sb.append(noteText).append("\n");
            }
            title_Text.setText(title_sb.toString());
            note_Text.setText(note_sb.toString());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (note_fis!=null || title_fis!=null){
                try {
                    title_fis.close();
                    note_fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public void deleteNote(){
        File dir = getFilesDir();
        File TITLE_file = new File(dir,TITLE_FILE_NAME);
        File NOTE_file = new File(dir, NOTE_FILE_NAME);
        boolean TITLE_deleted = TITLE_file.delete();
        boolean NOTE_deleted = NOTE_file.delete();
        //title_Text.setText("");
        //note_Text.setText("");
        SharedPreferences settings = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        settings.edit().clear().commit();
        finish();
    }
    @Override
    public void OnBottomSheet_ButtonClicked(String text) {
        View view = this.getWindow().getDecorView();
        ActionBar actionBar=getSupportActionBar();
        switch (text){
            case "Red":
                view.setBackgroundColor(Color.parseColor("#EB5757"));
                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EB5757")));
                break;
            case"Blue":
                view.setBackgroundColor(Color.parseColor("#56CCF2"));
                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#56CCF2")));
                break;
            case"Green":
                view.setBackgroundColor(Color.parseColor("#6FCF97"));
                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6FCF97")));
                break;
            case "Yellow":
                view.setBackgroundColor(Color.parseColor("#F2C94C"));
                break;
            case "Orange":
                view.setBackgroundColor(Color.parseColor("#F2994A"));
                break;
            case "White":
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case "Move To Trash":
                deleteNote();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        // your code.
        saveData();
        super.onBackPressed();
    }
}
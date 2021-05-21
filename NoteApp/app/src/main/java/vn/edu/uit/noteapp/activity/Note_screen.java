package vn.edu.uit.noteapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import vn.edu.uit.noteapp.Checkbox_recyclerview_items;
import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.NotesDatabase;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Checkbox_recyclerview_adapter;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;

public class Note_screen extends AppCompatActivity implements Note_Screen_Bottom_Sheet_Setting.BottomSheetListener  {

    private String note_screen_color;
    private  boolean CRI_visibility=false;

    private static final String TITLE_FILE_NAME = "exampleTITLE.txt";
    private static final String NOTE_FILE_NAME = "exampleNOTE.txt";

    //shared preferences
    public static final String SHARE_PREFS = "sharedPrefs";
    public static final String NOTE_SCREEN_COLOR = "note_screen_color";
    public static final String CRI_VISIBILITY_STATE = "INVISIBLE DEFAULT";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText title_Text, note_Text, Add_CRI_Etext;
    ImageButton show_CheckBox;
    Button Add_CRI_Btton;
    LinearLayout Add_CRI_Views;
    TextView noteDateTime;

    public ArrayList<Checkbox_recyclerview_items> checkboxRecyclerviewItems= new ArrayList<>();
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

        noteDateTime=findViewById(R.id.noteDateTime);

        Add_CRI_Btton = findViewById(R.id.add_Checkbox_RecyclerView_items_Btton);
        Add_CRI_Etext = findViewById(R.id.add_Checkbox_RecyclerView_items_Etext);
        Add_CRI_Views = findViewById(R.id.add_CRI_views);

        noteDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );

        show_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sync_EditText_With_CheckBox_RecyclerView();

                if (note_Text.getVisibility()==View.VISIBLE && mRecyclerView.getVisibility()==View.INVISIBLE){
                    note_Text.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    Add_CRI_Views.setVisibility(View.VISIBLE);

                }
                else {
                    note_Text.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    Add_CRI_Views.setVisibility(View.INVISIBLE);
                }

            }
        });

        Add_CRI_Btton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_CRI();
            }
        });

        //handling checkbox recyclerview
        mRecyclerView = findViewById(R.id.checkbox_recyclerview_layout);
        mRecyclerView.setHasFixedSize(true); // if recycler view don't change set this to true note: will delete later
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);


        //share preferences and save txt file
//        loadData();
//        updateData();

    }

    public void Sync_EditText_With_CheckBox_RecyclerView()
    {
        if (note_Text.getVisibility()==View.VISIBLE)
            checkboxRecyclerviewItems.clear();
        else {
            note_Text.setText("");
            for (int i = 0; i < checkboxRecyclerviewItems.size(); i++) {
                //template: String title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.title)).getText().toString();
                String tempE= ((EditText) mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkbox_edittext)).getText().toString();
                note_Text.setText(note_Text.getText() + tempE + "\n");
            }
        }
        String temp = note_Text.getText().toString();
        String lines[] = temp.split("\\r?\\n");
        for (int i=0;i<lines.length;i++)
            checkboxRecyclerviewItems.add(new Checkbox_recyclerview_items(lines[i],false));
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void Add_CRI(){
        int position=checkboxRecyclerviewItems.size();
        checkboxRecyclerviewItems.add(position,new Checkbox_recyclerview_items(Add_CRI_Etext.getText().toString(),false));
        Add_CRI_Etext.setText("");
        mAdapter.notifyItemInserted(position);
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

                finish();
                return true;
            case R.id.SaveNote:
                Sync_EditText_With_CheckBox_RecyclerView();
                //saveData();
                saveNote_V2();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void saveNote_V2(){
        //get Note background color to save
        View view = this.getWindow().getDecorView();
        Drawable background = view.getBackground();
        int colorID = ((ColorDrawable) background).getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorID));


        //If title doesn't have text send an alert message
        if (title_Text.getText().toString().trim().isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Missing note Title");
            alertDialog.setMessage("Note need to have a Title");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        final Note note = new Note();
        note.setTitle(title_Text.getText().toString());
        note.setNoteText(note_Text.getText().toString());
        note.setDateTime(noteDateTime.getText().toString());
        note.setColor(hexColor);

        class SaveNoteTask extends AsyncTask <Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoids) {
                super.onPostExecute(aVoids);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent); //return ra result code after activity end by saving
                finish();
            }
        }
        new SaveNoteTask().execute();
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
        if (mRecyclerView.getVisibility()==View.INVISIBLE)
            editor.putBoolean(CRI_VISIBILITY_STATE,false);
        else editor.putBoolean(CRI_VISIBILITY_STATE,true);
        editor.apply();
        saveNote();
    }
    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        note_screen_color = sharedPreferences.getString(NOTE_SCREEN_COLOR,"#FFFFFF");
        CRI_visibility=sharedPreferences.getBoolean(CRI_VISIBILITY_STATE,false);

    }
    public void updateData()
    {
        loadNote();
        if (CRI_visibility)
        {
            Sync_EditText_With_CheckBox_RecyclerView();
            mRecyclerView.setVisibility(View.VISIBLE);
            Add_CRI_Views.setVisibility(View.VISIBLE);
            note_Text.setVisibility(View.INVISIBLE);
        }
        else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            Add_CRI_Views.setVisibility(View.INVISIBLE);
        }
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
        Sync_EditText_With_CheckBox_RecyclerView();
      
//        saveData();
        saveNote_V2();
        super.onBackPressed();
    }
}
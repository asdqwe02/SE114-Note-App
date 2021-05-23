package vn.edu.uit.noteapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.Locale;

import vn.edu.uit.noteapp.Checkbox_recyclerview_items;
import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.NotesDatabase;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Checkbox_recyclerview_adapter;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;

public class Note_screen extends AppCompatActivity implements Note_Screen_Bottom_Sheet_Setting.BottomSheetListener {

    private String note_screen_color;
    private boolean CRI_visibility = false;

    public static final String SHARE_PREFS = "sharedPrefs";
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Note alreadyAvailableNote;
    private ImageView imageNote;
    private AlertDialog dialogDeleteNote;

    EditText title_Text, note_Text, Add_CRI_Etext;
    ImageButton show_CheckBox, addImage;
    Button Add_CRI_Btton;
    LinearLayout Add_CRI_Views;
    TextView noteDateTime;

    public ArrayList<Checkbox_recyclerview_items> checkboxRecyclerviewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        title_Text = findViewById(R.id.titleText);
        note_Text = findViewById(R.id.noteText);
        show_CheckBox = findViewById(R.id.show_checkbox);
        noteDateTime = findViewById(R.id.noteDateTime);
        addImage = findViewById(R.id.addNoteImage);
        imageNote = findViewById(R.id.imageNote);


        Add_CRI_Btton = findViewById(R.id.add_Checkbox_RecyclerView_items_Btton);
        Add_CRI_Etext = findViewById(R.id.add_Checkbox_RecyclerView_items_Etext);
        Add_CRI_Views = findViewById(R.id.add_CRI_views);

        Add_CRI_Views.setVisibility(View.INVISIBLE);


        noteDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );

        show_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Sync_EditText_With_CheckBox_RecyclerView();

                if (note_Text.getVisibility() == View.VISIBLE && mRecyclerView.getVisibility() == View.INVISIBLE) {
                    note_Text.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    Add_CRI_Views.setVisibility(View.VISIBLE);

                } else {
                    note_Text.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    Add_CRI_Views.setVisibility(View.INVISIBLE);
                }

            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            Note_screen.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectImage();
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

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            loadNote_V2();
        }



    }

    public void Sync_EditText_With_CheckBox_RecyclerView() {
        if (note_Text.getVisibility() == View.VISIBLE)
            checkboxRecyclerviewItems.clear();
        else {
            note_Text.setText("");
            for (int i = 0; i < checkboxRecyclerviewItems.size(); i++) {
                //template: String title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.title)).getText().toString();
                String tempE = ((EditText) mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkbox_edittext)).getText().toString();
                note_Text.setText(note_Text.getText() + tempE + "\n");
            }
        }
        String temp = note_Text.getText().toString();
        String lines[] = temp.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++)
            checkboxRecyclerviewItems.add(new Checkbox_recyclerview_items(lines[i], false));
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    public void Add_CRI() {
        int position = checkboxRecyclerviewItems.size();
        checkboxRecyclerviewItems.add(position, new Checkbox_recyclerview_items(Add_CRI_Etext.getText().toString(), false));
        Add_CRI_Etext.setText("");
        mAdapter.notifyItemInserted(position);
    }

    public void Open_Bottom_Sheet_Setting() {
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

    private void saveNote_V2() {
        //get Note background color to save
        View view = this.getWindow().getDecorView();
        Drawable background = view.getBackground();
        int colorID = ((ColorDrawable) background).getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorID));


        //If title doesn't have text send an alert message
        if (title_Text.getText().toString().trim().isEmpty()) {
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
        if (note_Text.getVisibility() == View.VISIBLE && mRecyclerView.getVisibility() == View.INVISIBLE)
            note.setCRIstate(false);
        else note.setCRIstate(true);

        if (alreadyAvailableNote != null) {
            note.setId(alreadyAvailableNote.getId());
        }

        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoids) {
                super.onPostExecute(aVoids);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent); //return ra result code after activity end by saving
                finish();
            }
        }
        new SaveNoteTask().execute();
    }

    private void loadNote_V2() {
        title_Text.setText(alreadyAvailableNote.getTitle());
        note_Text.setText(alreadyAvailableNote.getNoteText());
        noteDateTime.setText(alreadyAvailableNote.getDateTime());

        note_screen_color = alreadyAvailableNote.getColor();
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor(note_screen_color));

        if (alreadyAvailableNote.isCRIstate()) {
            Sync_EditText_With_CheckBox_RecyclerView();
            mRecyclerView.setVisibility(View.VISIBLE);
            Add_CRI_Views.setVisibility(View.VISIBLE);
            note_Text.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            Add_CRI_Views.setVisibility(View.INVISIBLE);
        }
    }

    private void showDeleteNoteDialog(){
        if (dialogDeleteNote==null){
            AlertDialog.Builder builder =  new AlertDialog.Builder(Note_screen.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.delete_dialog,
                    (ViewGroup) findViewById(R.id.DeleteNoteContainer)
            );
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if (dialogDeleteNote.getWindow() != null){
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class DeleteNoteTask extends AsyncTask <Void, Void, Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao()
                                    .deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });
            view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDeleteNote.dismiss();
                }
            });
        }
        dialogDeleteNote.show();
    }


    public void deleteNote() {
        finish();
    }

    @Override
    public void OnBottomSheet_ButtonClicked(String text) {
        View view = this.getWindow().getDecorView();
        ActionBar actionBar = getSupportActionBar();
        switch (text) {
            case "Red":
                view.setBackgroundColor(Color.parseColor("#EB5757"));
                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EB5757")));
                break;
            case "Blue":
                view.setBackgroundColor(Color.parseColor("#56CCF2"));
                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#56CCF2")));
                break;
            case "Green":
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
                if (alreadyAvailableNote!=null)
                    showDeleteNoteDialog();
                break;
            default:
                break;
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {

                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
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
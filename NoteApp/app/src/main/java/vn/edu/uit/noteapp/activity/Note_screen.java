package vn.edu.uit.noteapp.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.uit.noteapp.Checkbox_recyclerview_items;
import vn.edu.uit.noteapp.bottomsheet.Bottom_Sheet_Add_Notebook;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.entities.Note;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Checkbox_recyclerview_adapter;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;
import vn.edu.uit.noteapp.listeners.AlarmReceiver;
import vn.edu.uit.noteapp.listeners.NotebooksListener;

public class Note_screen extends AppCompatActivity implements
        Note_Screen_Bottom_Sheet_Setting.BottomSheetListener {

    public static final String CHANNEL_ID = "ChannelID";
    private String note_screen_color;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private RecyclerView mRecyclerView;
    private Checkbox_recyclerview_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Note alreadyAvailableNote;
    private ImageView imageNote;
    private AlertDialog dialogDeleteNote;
    private View CRI_View;
    private String selectedImagePath;
    private Context context;
    private boolean bookmark;
    public String notebookname;
    EditText title_Text, note_Text, Add_CRI_Etext;
    ImageButton show_CheckBox, addImage;
    Button Add_CRI_Btton;
    LinearLayout Add_CRI_Views;
    TextView noteDateTime, timeReminder, dateReminder;
    /****/
    final Calendar calendar = Calendar.getInstance();
    //
    private boolean reminder;
    private int mDay, mMonth, mYear, mHour, mMinute;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int rYear,rMonth,rDayOfMonth;

    public ArrayList<Checkbox_recyclerview_items> checkboxRecyclerviewItems = new ArrayList<>();

    public Note_screen(Note alreadyAvailableNote) {
        this.alreadyAvailableNote = alreadyAvailableNote;
    }

    public Note_screen() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Note");

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        context = this;
        title_Text = findViewById(R.id.titleText);
        note_Text = findViewById(R.id.noteText);
        show_CheckBox = findViewById(R.id.show_checkbox);
        noteDateTime = findViewById(R.id.noteDateTime);
        addImage = findViewById(R.id.addNoteImage);
        imageNote = findViewById(R.id.imageNote);
        CRI_View = findViewById(R.id.checkbox_recyclerview);
        bookmark = false;
        notebookname = "";
        timeReminder = findViewById(R.id.timeReminder);
        dateReminder = findViewById(R.id.dateReminder);
        reminder = false;

        Add_CRI_Btton = findViewById(R.id.add_Checkbox_RecyclerView_items_Btton);
        Add_CRI_Etext = findViewById(R.id.add_Checkbox_RecyclerView_items_Etext);
        Add_CRI_Views = findViewById(R.id.add_CRI_views);

        Add_CRI_Views.setVisibility(View.GONE);
        selectedImagePath = "";

        noteDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a",
                        Locale.getDefault()).format(new Date())
        );


        show_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sync_EditText_With_CheckBox_RecyclerView();
                if (note_Text.getVisibility() == View.VISIBLE
                        && mRecyclerView.getVisibility() == View.GONE) {
                    note_Text.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    Add_CRI_Views.setVisibility(View.VISIBLE);

                } else {
                    note_Text.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    Add_CRI_Views.setVisibility(View.GONE);
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
                Add_CRI(Add_CRI_Etext.getText().toString());
            }
        });
        Add_CRI_Etext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    Add_CRI(Add_CRI_Etext.getText().toString());
                    return true;
                }
                return false;
            }
        });


        findViewById(R.id.imageRemoveImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageNote.setImageBitmap(null);
                imageNote.setVisibility(View.GONE);
                findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
                Formatting_View_To_Fit_Added_Image(false);
                selectedImagePath = "";
            }
        });


        //handling checkbox recyclerview
        mRecyclerView = findViewById(R.id.checkbox_recyclerview_layout);
        mRecyclerView.setHasFixedSize(true); // if recycler view don't change set this to true note: will delete later
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.GONE);

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            loadNote_V2();
            if (getIntent().getBooleanExtra("deleteWithSwipe", false)) {
                showDeleteNoteDialog(Note_screen.this, dialogDeleteNote);
            }
        }

        if (getIntent().getBooleanExtra("Delete Reminder", false)) {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            loadNote_V2();
            if (getIntent().getBooleanExtra("SwipeToDelete", false)) {
                deleteReminder();
            }
        }

        if (getIntent().getBooleanExtra("Edit Reminder", false)) {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            loadNote_V2();
            if (getIntent().getBooleanExtra("SwipeToEdit", false)) {
                pickDate();
            }
        }


        //
        createNotificationChannel();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    }

    /*--------------------function to call date picker dialog--------------------------*/

    private void pickDate() {
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                R.style.datePicker,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //calendar.set(year, month, dayOfMonth);
                        rYear=year;
                        rMonth=month;
                        rDayOfMonth=dayOfMonth;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dateReminder.setText(simpleDateFormat.format(calendar.getTime()));
                        Log.d("calendarSet", "onDateSet: " + "butt"+ calendar.getTime().toString());
                        pickTime();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /*--------------------function to call time picker dialog--------------------------*/

    public void pickTime() {
        mMinute = calendar.get(Calendar.MINUTE);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                R.style.timePicker,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        /**/
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(
                                getApplicationContext(),
                                alreadyAvailableNote.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        createNotificationChannel();
                        //

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        Log.d("checkDateSet", "onTimeSet: " + calendar.getTime());
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.YEAR, rYear);
                        calendar.set(Calendar.MONTH, rMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, rDayOfMonth);
                        timeReminder.setText(simpleDateFormat.format(calendar.getTime()));
                        reminder = true;
                        alreadyAvailableNote.setReminder(reminder);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), pendingIntent);
                        saveNote_V2();
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    /**/
    private void createNotificationChannel() {
        CharSequence name = "reminder channel";
        String description = "Easy note";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public int getNoteID() {
        return alreadyAvailableNote.getId();
    }

    public void cancelAlarm() {
        //alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

//        if (alarmManager != null) {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(context, "Cancel alarm", Toast.LENGTH_SHORT).show();
//        }
    }
    //

    public void Sync_EditText_With_CheckBox_RecyclerView() {

        if (note_Text.getVisibility() == View.VISIBLE) {
            checkboxRecyclerviewItems.clear();
        } else {
            //sync for note text
            note_Text.setText("");
            ArrayList<Checkbox_recyclerview_items> tempCRI_List = new ArrayList<>();
            tempCRI_List = mAdapter.getCri_LIST();
            for (int i = 0; i < tempCRI_List.size(); i++) {
                String tempE = tempCRI_List.get(i).get_checkbox_edittext();
                note_Text.setText(note_Text.getText() + tempE + "\n");
            }

        }
        //sync for checkbox
        String temp = note_Text.getText().toString();
        String lines[] = temp.split("\\r?\\n");
        if (!temp.isEmpty())
            for (int i = 0; i < lines.length; i++)
                checkboxRecyclerviewItems.add(new Checkbox_recyclerview_items(lines[i], false));
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void Add_CRI(String text) {
        int position = checkboxRecyclerviewItems.size();
        checkboxRecyclerviewItems.add(position, new Checkbox_recyclerview_items(text, false));
        Add_CRI_Etext.setText("");
        mAdapter.notifyItemInserted(position);
        /*Reuse code in Sync_EditText_With_CheckBox_RecyclerView() to update the recycler view
         this shit is dumb as fuck but it work, it does make the file bloated tho*/
//
        ArrayList<Checkbox_recyclerview_items> tempCRI_List = new ArrayList<>();
        tempCRI_List = mAdapter.getCri_LIST();
        String lines[] = new String[tempCRI_List.size()];
        for (int i = 0; i < tempCRI_List.size(); i++) {
            lines[i] = tempCRI_List.get(i).get_checkbox_edittext();
        }
        checkboxRecyclerviewItems.clear();
        for (int i = 0; i < lines.length; i++)
            checkboxRecyclerviewItems.add(new Checkbox_recyclerview_items(lines[i], false));
        mAdapter = new Checkbox_recyclerview_adapter(checkboxRecyclerviewItems);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void Open_Bottom_Sheet_Setting() {
        Note_Screen_Bottom_Sheet_Setting bottomSheet = new Note_Screen_Bottom_Sheet_Setting();
        Bundle bundleBookmarkReminder = new Bundle();
        if (alreadyAvailableNote != null){
            bundleBookmarkReminder.putBoolean("Bookmarked", alreadyAvailableNote.isBookmark());
            bundleBookmarkReminder.putBoolean("Remindered", alreadyAvailableNote.isReminder());
        }
        else {
            bundleBookmarkReminder.putBoolean("Bookmarked", false);
            bundleBookmarkReminder.putBoolean("Reminder", false);
        }
        bottomSheet.setArguments(bundleBookmarkReminder);

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
                if (!title_Text.getText().toString().isEmpty()) {
                    Sync_EditText_With_CheckBox_RecyclerView();
                    saveNote_V2();
                } else finish();
                return true;
            case R.id.SaveNote:
                Sync_EditText_With_CheckBox_RecyclerView();
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

        /**/
        note.setReminderDate(dateReminder.getText().toString());
        note.setReminderTime(timeReminder.getText().toString());
        //

        if (alreadyAvailableNote != null) {
            bookmark = alreadyAvailableNote.isBookmark();
            notebookname = alreadyAvailableNote.getNotebook();
        }
        note.setBookmark(bookmark);
        note.setNotebook(notebookname);
        note.setColor(hexColor);
        note.setImagePath(selectedImagePath);

        /**/
        if (alreadyAvailableNote != null) {
            reminder = alreadyAvailableNote.isReminder();
        }
        note.setReminder(reminder);
        //

        if (note_Text.getVisibility() == View.VISIBLE && mRecyclerView.getVisibility() == View.GONE)
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

    public void saveNote_V2_FromOutside(Context outsideContext) {
        final Note note;
        if (alreadyAvailableNote != null)
            note = alreadyAvailableNote;
        else return;
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getNotesDatabase(outsideContext).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoids) {
                super.onPostExecute(aVoids);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent); //return ra result code after activity end by saving
            }
        }
        new SaveNoteTask().execute();
    }

    private void loadNote_V2() {
        title_Text.setText(alreadyAvailableNote.getTitle());
        note_Text.setText(alreadyAvailableNote.getNoteText());
        noteDateTime.setText(alreadyAvailableNote.getDateTime());

        /**/
        dateReminder.setText(alreadyAvailableNote.getReminderDate());
        timeReminder.setText(alreadyAvailableNote.getReminderTime());
        //

        View view = this.getWindow().getDecorView();
        note_screen_color = alreadyAvailableNote.getColor();

        if (note_screen_color.equals("#FAFAFA")
                || note_screen_color.equals("#FFFFFF")
                || note_screen_color.equals("#303030")) {
            int currentNightMode = context.getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    view.setBackgroundColor(Color.parseColor("#303030"));
                    break;
            }
        } else {
            view.setBackgroundColor(Color.parseColor(note_screen_color));
        }


        if (alreadyAvailableNote.getImagePath() != null && !alreadyAvailableNote
                .getImagePath().trim().isEmpty()) {
            imageNote.setImageBitmap(BitmapFactory.decodeFile(alreadyAvailableNote.getImagePath()));
            imageNote.setVisibility(View.VISIBLE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
            Formatting_View_To_Fit_Added_Image(true);
            selectedImagePath = alreadyAvailableNote.getImagePath();

        } else {
            imageNote.setVisibility(View.GONE);
            Formatting_View_To_Fit_Added_Image(false);
        }

        if (alreadyAvailableNote.isCRIstate()) {
            Sync_EditText_With_CheckBox_RecyclerView();
            mRecyclerView.setVisibility(View.VISIBLE);
            Add_CRI_Views.setVisibility(View.VISIBLE);
            note_Text.setVisibility(View.GONE);
        } else {
            note_Text.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            Add_CRI_Views.setVisibility(View.GONE);
        }
    }

    public void showDeleteNoteDialog(Context deleteContextFrom, AlertDialog dialogDeleteNoteFrom) {
        if (dialogDeleteNoteFrom == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(deleteContextFrom);
            View view = LayoutInflater.from(deleteContextFrom).inflate(
                    R.layout.delete_dialog,
                    null
            );
            view.findViewById(R.id.DeleteNoteContainer);
            builder.setView(view);
            dialogDeleteNoteFrom = builder.create();
            if (dialogDeleteNoteFrom.getWindow() != null) {
                dialogDeleteNoteFrom.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            AlertDialog finalDialogDeleteNoteFrom = dialogDeleteNoteFrom;
            view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void> {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getNotesDatabase(deleteContextFrom).noteDao()
                                    .deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    new DeleteNoteTask().execute();
                }
            });
            view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalDialogDeleteNoteFrom.dismiss();
                    if (getIntent().getBooleanExtra("deleteWithSwipe", false))
                        finish();
                }
            });
        }
        dialogDeleteNoteFrom.show();
    }

    /**/
    public void deleteReminder() {
        if (alreadyAvailableNote.isReminder() == true) {
            reminder = false;
            alreadyAvailableNote.setReminder(reminder);
            Intent intent = new Intent(Note_screen.this, Reminder_screen.class);
            intent.putExtra("isNoteDeleted", true);
            setResult(RESULT_OK, intent);
            dateReminder.setText("no");
            timeReminder.setText("");
            saveNote_V2();
            loadNote_V2();
            startActivity(intent);
        }
    }


    public void deleteNote(boolean delete) {
        delete = true;
        Log.d("isDeleted with swipe boolean", "isDeleted: " + delete);
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
                int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        view.setBackgroundColor(Color.parseColor("#303030"));
                        break;
                }
                break;
            case "Move To Trash":
                if (alreadyAvailableNote != null) {
                    showDeleteNoteDialog(Note_screen.this, dialogDeleteNote);
                } else {
                    finish();
                }
                break;
            case "Bookmark This Note":
                if (alreadyAvailableNote != null) {
                    add_to_bookmark();
                }
                break;
            case "Move To Notebook":
                if (alreadyAvailableNote != null) {
                    move_to_notebook();
                }
                break;
            case "Add reminder":
                if (alreadyAvailableNote != null) {
                    add_to_reminder();
                }
                break;
            default:
                break;
        }
    }

    private void move_to_notebook() {
        ArrayList<Model_Item_Notebook_screen> item_model = new ArrayList<>();

        //open bottomsheet add notebook
        Bottom_Sheet_Add_Notebook bottomsheet = new Bottom_Sheet_Add_Notebook(item_model, new NotebooksListener() {
            @Override
            public void OnNotebookClicked(Model_Item_Notebook_screen notebook, int position, boolean update) {
                if (alreadyAvailableNote.getNotebook().equals("")) {
                    Toast.makeText(Note_screen.this, notebook.getItem_name(), Toast.LENGTH_SHORT).show();
                    alreadyAvailableNote.setNotebook(notebook.getItem_name());
                    saveNote_V2();
                } else if (!alreadyAvailableNote.getNotebook().equals(notebook.getItem_name())) {
                    Toast.makeText(Note_screen.this, notebook.getItem_name(), Toast.LENGTH_SHORT).show();
                    alreadyAvailableNote.setNotebook(notebook.getItem_name());
                    Intent intent = new Intent(Note_screen.this, MainActivity.class);
                    intent.putExtra("isNoteDeleted", true);
                    setResult(RESULT_OK, intent);
                    finish();
                    saveNote_V2();
                    startActivity(intent);
                } else if (alreadyAvailableNote.getNotebook().equals(notebook.getItem_name())) {
                    Toast.makeText(Note_screen.this, "Remove complete!", Toast.LENGTH_SHORT).show();
                    alreadyAvailableNote.setNotebook("");
                    Intent intent = new Intent(Note_screen.this, MainActivity.class);
                    intent.putExtra("isNoteDeleted", true);
                    setResult(RESULT_OK, intent);
                    finish();
                    saveNote_V2();
                    startActivity(intent);
                }

            }

        });
        bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
    }

    private void add_to_bookmark() {
        if (alreadyAvailableNote.isBookmark() == false) {
            bookmark = true;
            alreadyAvailableNote.setBookmark(bookmark);
            saveNote_V2();
        } else if (alreadyAvailableNote.isBookmark() == true) {
            bookmark = false;
            alreadyAvailableNote.setBookmark(bookmark);
            Intent intent = new Intent(Note_screen.this, MainActivity.class);
            intent.putExtra("isNoteDeleted", true);
            setResult(RESULT_OK, intent);
            finish();
            saveNote_V2();
            startActivity(intent);
        }

    }

    /**/
    private void add_to_reminder() {
        if (alreadyAvailableNote.isReminder() == false) {
            pickDate();
        } else if (alreadyAvailableNote.isReminder()) {
            reminder = false;
            alreadyAvailableNote.setReminder(reminder);
            Intent intent = new Intent(Note_screen.this, MainActivity.class);
            intent.putExtra("isNoteDeleted", true);
            setResult(RESULT_OK, intent);
            dateReminder.setText("no");
            timeReminder.setText("");
            cancelAlarm();
            saveNote_V2();
            loadNote_V2();
            startActivity(intent);
        }
    }
    //

    public void Formatting_View_To_Fit_Added_Image(boolean b) {

        //change note text and CRI view to fit in with the image just added
        RelativeLayout.LayoutParams note_textLayoutParams
                = (RelativeLayout.LayoutParams) note_Text.getLayoutParams();
        RelativeLayout.LayoutParams cri_viewLayoutParams
                = (RelativeLayout.LayoutParams) CRI_View.getLayoutParams();
        if (b) {

            note_textLayoutParams.addRule(RelativeLayout.BELOW, R.id.imageNote);
            note_Text.setLayoutParams(note_textLayoutParams); //causes layout update;

            cri_viewLayoutParams.addRule(RelativeLayout.BELOW, R.id.imageNote);
            CRI_View.setLayoutParams(cri_viewLayoutParams);
        } else {
            note_textLayoutParams.addRule(RelativeLayout.BELOW, R.id.normal_view);
            note_Text.setLayoutParams(note_textLayoutParams); //causes layout update

            cri_viewLayoutParams.addRule(RelativeLayout.BELOW, R.id.normal_view);
            CRI_View.setLayoutParams(cri_viewLayoutParams);
        }
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //if (intent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        Formatting_View_To_Fit_Added_Image(true);
        //}
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
                        findViewById(R.id.imageRemoveImage).setVisibility(View.VISIBLE);
                        selectedImagePath = getPathFromUri(selectedImageUri);

                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }


    @Override
    public void onBackPressed() {
        // your code.
        if (!title_Text.getText().toString().isEmpty()) {
            Sync_EditText_With_CheckBox_RecyclerView();
            saveNote_V2();
        }
        super.onBackPressed();
    }

}
package vn.edu.uit.noteapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import vn.edu.uit.noteapp.bottomsheet.Bottom_Sheet_Notebookscreen;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.entities.Note;
import vn.edu.uit.noteapp.entities.NotebooksDatabase;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.adapter.Notebookscreen_recyclerview_adapter;
import vn.edu.uit.noteapp.listeners.NotebooksListener;


public class Notebook_Screen extends AppCompatActivity implements NotebooksListener,
        Bottom_Sheet_Notebookscreen.NoteBookBottomSheetListener {
    public static final int REQUEST_CODE_ADD_NOTEBOOK = 1;
    public static final int REQUEST_CODE_UPDATE_NOTEBOOK = 2;
    public static final int REQUEST_CODE_SHOW_NOTEBOOKS = 3;
    private int notebookClickedPosition = -1;


    ArrayList<Model_Item_Notebook_screen> item_model;
    Notebookscreen_recyclerview_adapter recycler_adapter;
    //NotebooksListener listener;
    RecyclerView recyclerView;
    ImageButton addbutton;
    public String name;

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
                if (window == null) {
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
                        name = editName.getText().toString();
                        if (name.matches("")) {
                            Toast.makeText(Notebook_Screen.this, "Please enter your notebook name", Toast.LENGTH_SHORT).show();
                        } else {
//                            item_model.add(new Model_Item_Notebook_screen(editName.getText().toString()));
                            //save Notebook
                            SaveNotebook(null);
                            dialog.dismiss();
                            Toast.makeText(Notebook_Screen.this, "Create success", Toast.LENGTH_SHORT).show();
                            getNotebooks(REQUEST_CODE_ADD_NOTEBOOK, false);
                        }
                    }
                });
                dialog.show();
            }
        });
        recyclerView = findViewById(R.id.recycle_main);
        item_model = new ArrayList<>();
//        CreateItem();
        //Click Item in Notebook
        recycler_adapter = new Notebookscreen_recyclerview_adapter(this, item_model, this);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getNotebooks(REQUEST_CODE_SHOW_NOTEBOOKS, false);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveNotebook(Model_Item_Notebook_screen updateMoteBook) {
        final Model_Item_Notebook_screen notebook = new Model_Item_Notebook_screen();
        if (updateMoteBook != null)
            notebook.setId(updateMoteBook.getId());
        notebook.setItem_name(name);
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotebooksDatabase.getNotebooksDatabase(getApplicationContext()).notebookDAO().insertNote(notebook);
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

    @SuppressLint("StaticFileLeak")
    public void getNotebooks(final int requestCode, final boolean isNotebookDeleted) {
        class GetNotebookTask extends AsyncTask<Void, Void, List<Model_Item_Notebook_screen>> {
            @Override
            protected List<Model_Item_Notebook_screen> doInBackground(Void... voids) {
                return NotebooksDatabase
                        .getNotebooksDatabase(getApplicationContext())
                        .notebookDAO().getNotebook();
            }

            @Override
            protected void onPostExecute(List<Model_Item_Notebook_screen> notebook) {
                super.onPostExecute(notebook);
                Log.d("MY_NOTEBOOK", notebook.toString());
                if (requestCode == REQUEST_CODE_SHOW_NOTEBOOKS) {
                    item_model.addAll(notebook);
                    recycler_adapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_NOTEBOOK) {
                    item_model.add(0, notebook.get(0));
                    recycler_adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                    refreshNotebookData();
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTEBOOK) {
                    item_model.remove(notebookClickedPosition);
                    if (isNotebookDeleted) {
                        recycler_adapter.notifyItemRemoved(notebookClickedPosition);
                    } else {
                        item_model.add(notebookClickedPosition, notebook.get(notebookClickedPosition));
                        recycler_adapter.notifyItemChanged(notebookClickedPosition);

                    }

                }

            }
        }
        new GetNotebookTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTEBOOK && resultCode == RESULT_OK) {
            getNotebooks(REQUEST_CODE_ADD_NOTEBOOK, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTEBOOK && resultCode == RESULT_OK) {
            if (data != null)
                getNotebooks(REQUEST_CODE_UPDATE_NOTEBOOK, data.getBooleanExtra("isNotebookDeleted", false));
        }
    }

    @Override
    public void OnNotebookClicked(Model_Item_Notebook_screen notebook, int position,
                                  boolean update) {
        notebookClickedPosition = position;
        if (!update) {
            Intent intent = new Intent(getApplicationContext(), Notebook_activity.class);
            intent.putExtra("notebook", notebook.getItem_name());
            startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTEBOOK);
        } else {
            final Dialog dialog;
            dialog = new Dialog(Notebook_Screen.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_notebook_dialog);

            Window window = dialog.getWindow();
            if (window == null) {
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
            TextView dialogTilte = dialog.findViewById(R.id.processNotebookTitle);
            dialogTilte.setText("Rename Notebook?");
            btn_Create.setText("Rename");

            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn_Create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = editName.getText().toString();
                    if (name.matches("")) {
                        Toast.makeText(Notebook_Screen.this, "Please enter your notebook name", Toast.LENGTH_SHORT).show();
                    } else {
                        // item_model.add(new Model_Item_Notebook_screen(editName.getText().toString()));
                        //save Notebook
                        SaveNotebook(notebook);
                        dialog.dismiss();
                        Toast.makeText(Notebook_Screen.this, "Rename Success", Toast.LENGTH_SHORT).show();
                        getNotebooks(REQUEST_CODE_UPDATE_NOTEBOOK, false);

                       /*Rename every note that have notebook.getItem_name() in it and
                        change it into the new notebook name*/
                        final Context notebookContext = getApplicationContext();
                        class RenameNoteBookInNoteTask extends AsyncTask<Void, Void, List<Note>> {
                            @Override
                            protected List<Note> doInBackground(Void... voids) {
                                return NotesDatabase
                                        .getNotesDatabase(getApplicationContext())
                                        .noteDao().getNotebookNote(notebook.getItem_name());
                            }

                            @Override
                            protected void onPostExecute(List<Note> notes) {
                                super.onPostExecute(notes);
                                for (int i = 0; i < notes.size(); i++) {
                                    notes.get(i).setNotebook(name);
                                    Note_screen temp = new Note_screen(notes.get(i));
                                    temp.saveNote_V2_FromOutside(notebookContext);
                                }
                            }
                        }
                        new RenameNoteBookInNoteTask().execute();
                    }
                }
            });
            dialog.show();
        }
    }
    private void refreshNotebookData() {
        item_model.clear();
        recycler_adapter = new Notebookscreen_recyclerview_adapter(this, item_model, this);
        recyclerView.setAdapter(recycler_adapter);
        getNotebooks(REQUEST_CODE_SHOW_NOTEBOOKS, false);
    }
    private void reloadActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onDeleteButtonClick(Boolean deleted) {
        if (deleted)
            refreshNotebookData();
    }
}
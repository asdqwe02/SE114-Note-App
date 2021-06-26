package vn.edu.uit.noteapp.bottomsheet;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Adapter_For_AddNotebook;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.entities.NotebooksDatabase;
import vn.edu.uit.noteapp.listeners.NotebooksListener;

import static android.app.Activity.RESULT_OK;

public class Bottom_Sheet_Add_Notebook  extends BottomSheetDialogFragment {
    public static final int REQUEST_CODE_ADD_NOTEBOOK = 1;
    public static final int REQUEST_CODE_UPDATE_NOTEBOOK = 2;
    public static final int REQUEST_CODE_SHOW_NOTEBOOKS = 3;
    private int notebookClickedPosition = -1;

    ArrayList<Model_Item_Notebook_screen> item_model;
    NotebooksListener listener;
    Adapter_For_AddNotebook adapter;
    RecyclerView recyclerView;
    //tẽtview show No Notebook
    TextView No_Noteebook;
    public Bottom_Sheet_Add_Notebook(ArrayList<Model_Item_Notebook_screen> item_model, NotebooksListener listener){
        this.item_model = item_model;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.bottomsheet_addnotebook,null);
        bottomSheetDialog.setContentView(v);
        RecyclerView recyclerView = v.findViewById(R.id.bottomsheet_addnotebook);
        No_Noteebook = v.findViewById(R.id.No_Noteebook_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_For_AddNotebook(getContext(), item_model, new NotebooksListener() {
            @Override
            public void OnNotebookClicked(Model_Item_Notebook_screen notebook, int position,boolean update) {
                listener.OnNotebookClicked(notebook , position,false);
            }

        });
        getNotebooks(REQUEST_CODE_SHOW_NOTEBOOKS, false);
        recyclerView.setAdapter(adapter);
        //Show textview

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return bottomSheetDialog;

    }
    @SuppressLint("StaticFileLeak")
    public void getNotebooks(final int requestCode, final boolean isNotebookDeleted) {
        class GetNotebookTask extends AsyncTask<Void, Void, List<Model_Item_Notebook_screen>> {
            @Override
            protected List<Model_Item_Notebook_screen> doInBackground(Void... voids) {
                return NotebooksDatabase
                        .getNotebooksDatabase(getContext())
                        .notebookDAO().getNotebook();
            }

            @Override
            protected void onPostExecute(List<Model_Item_Notebook_screen> notebook) {
                super.onPostExecute(notebook);
                Log.d("MY_NOTEBOOK", notebook.toString());

                if (requestCode == REQUEST_CODE_SHOW_NOTEBOOKS) {
                    item_model.addAll(notebook);
                    adapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_NOTEBOOK) {
                    item_model.add(0, notebook.get(0));
                    adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTEBOOK) {
                    item_model.remove(notebookClickedPosition);
                    if (isNotebookDeleted){
                        adapter.notifyItemRemoved(notebookClickedPosition);
                    } else{
                        item_model.add(notebookClickedPosition, notebook.get(notebookClickedPosition));
                        adapter.notifyItemChanged(notebookClickedPosition);
                    }

                }
                if(adapter.getItemCount() == 0)
                {
                    Log.d("notebook_item_count",  String.valueOf(adapter.getItemCount()));
                    No_Noteebook.setVisibility(View.VISIBLE);
                }
            }
        }
        new GetNotebookTask().execute();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTEBOOK && resultCode == RESULT_OK) {
            getNotebooks(REQUEST_CODE_ADD_NOTEBOOK, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTEBOOK && resultCode == RESULT_OK) {
            if (data != null)
                getNotebooks(REQUEST_CODE_UPDATE_NOTEBOOK, data.getBooleanExtra("isNotebookDeleted",false));
        }
    }
}

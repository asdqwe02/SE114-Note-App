package vn.edu.uit.noteapp.adapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.activity.Note_screen;
import vn.edu.uit.noteapp.bottomsheet.Bottom_Sheet_Notebookscreen;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.listeners.NotebooksDatabase;

import vn.edu.uit.noteapp.listeners.NotebooksListener;

public class Notebookscreen_recyclerview_adapter extends RecyclerView.Adapter<Notebookscreen_recyclerview_adapter.ViewHolder> {

    Context context;
    ArrayList<Model_Item_Notebook_screen> item_model;

    Model_Item_Notebook_screen item;

    public String title_text;
    public static int id;
    NotebooksListener listener;

    public Notebookscreen_recyclerview_adapter(Context context, ArrayList<Model_Item_Notebook_screen> item_model, NotebooksListener listener)
    {
        this.context = context;
        this.item_model = item_model;
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemtext_name;
        private ImageButton imgbtn;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemtext_name = itemView.findViewById(R.id.item_notebookscreen);
            imgbtn = itemView.findViewById(R.id.menu_item);
            title_text = itemtext_name.getText().toString();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.notebookscreen_recyclerview_adapter,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        item = item_model.get(position);
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // truyen vi tri va lay du lieu tu adpater
                item = item_model.get(position);
                Bottom_Sheet_Notebookscreen bottom_sheet_notebookscreen = new Bottom_Sheet_Notebookscreen(position,Notebookscreen_recyclerview_adapter.this);
                bottom_sheet_notebookscreen.show(((FragmentActivity)context).getSupportFragmentManager(),"example");
            }
        });

        holder.itemtext_name.setText(item.getItem_name());
        holder.itemtext_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    title_text = item.getItem_name();
                    listener.OnNotebookClicked(item_model.get(position),position);
            }
        });
    }
    @Override
    public int getItemCount(){
        return item_model.size();
    }

    public void remove()

    {
        class DeleteNoteBookTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotebooksDatabase.getNotebooksDatabase(context.getApplicationContext()).notebookDAO()
                        .deleteNote(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                intent.putExtra("isNoteDeleted", true);
            }
        }
        new DeleteNoteBookTask().execute();
    }

    public void deleteNotebook(int position)
    {
        class DeleteNoteBookTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                NotebooksDatabase.getNotebooksDatabase(context.getApplicationContext()).notebookDAO()
                        .deleteNote(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                intent.putExtra("isNoteDeleted", true);
            }
        }
        new DeleteNoteBookTask().execute();
    }

}

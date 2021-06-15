package vn.edu.uit.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.bottomsheet.Bottom_Sheet_Notebookscreen;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;
import vn.edu.uit.noteapp.listeners.NotebooksListener;

public class Adapter_For_AddNotebook extends RecyclerView.Adapter<Adapter_For_AddNotebook.ViewHolder> {
    Context context;
    ArrayList<Model_Item_Notebook_screen> item_model;

    Model_Item_Notebook_screen item;
    public static String title_text;
    NotebooksListener listener;

    public Adapter_For_AddNotebook(Context context, ArrayList<Model_Item_Notebook_screen> item_model, NotebooksListener listener)
    {
        this.context = context;
        this.item_model = item_model;
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemtext_name;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemtext_name = itemView.findViewById(R.id.tv_NotebookName);
            title_text = itemtext_name.getText().toString();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.item_add_notebook_bottomsheet,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        item = item_model.get(position);
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
}

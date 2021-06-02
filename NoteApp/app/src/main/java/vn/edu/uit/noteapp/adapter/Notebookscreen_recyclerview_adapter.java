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

public class Notebookscreen_recyclerview_adapter extends RecyclerView.Adapter<Notebookscreen_recyclerview_adapter.ViewHolder> {
    Context context;
    ArrayList<Model_Item_Notebook_screen> item_model;

    public Notebookscreen_recyclerview_adapter(Context context, ArrayList<Model_Item_Notebook_screen> item_model)
    {
        this.context = context;
        this.item_model = item_model;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemtext_name;
        private ImageButton imgbtn;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemtext_name = itemView.findViewById(R.id.item_notebookscreen);
            imgbtn = itemView.findViewById(R.id.menu_item);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.notebookscreen_recyclerview_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Model_Item_Notebook_screen item = item_model.get(position);
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // truyen vi tri va lay du lieu tu adpater
                Bottom_Sheet_Notebookscreen bottom_sheet_notebookscreen = new Bottom_Sheet_Notebookscreen(position,Notebookscreen_recyclerview_adapter.this);
                bottom_sheet_notebookscreen.show(((FragmentActivity)context).getSupportFragmentManager(),"example");
            }
        });
        holder.itemtext_name.setText(item.getItem_name());
    }
    @Override
    public int getItemCount(){
        return item_model.size();
    }

    public void remove(int position)
    {
        item_model.remove(position);
        notifyItemRemoved(position);
    }
}

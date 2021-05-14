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

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> {
    Context context;
    ArrayList<Model_Item> item_model;

    public Recycler_Adapter(Context context, ArrayList<Model_Item> item_model)
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
        View item_view = inflater.inflate(R.layout.item_recycler_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Model_Item item = item_model.get(position);
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // truyen vi tri va lay du lieu tu adpater
                Bottom_Sheet_Notebookscreen bottom_sheet_notebookscreen = new Bottom_Sheet_Notebookscreen(position,Recycler_Adapter.this);
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
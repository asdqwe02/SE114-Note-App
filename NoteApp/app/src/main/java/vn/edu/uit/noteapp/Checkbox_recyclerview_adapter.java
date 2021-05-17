package vn.edu.uit.noteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Checkbox_recyclerview_adapter extends RecyclerView.Adapter<Checkbox_recyclerview_adapter.Checkbox_recyclerview_ViewHolder> {
    private  ArrayList<Checkbox_recyclerview_items> cri_LIST;

    public static class Checkbox_recyclerview_ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public EditText checkBox_EditText;
        public Checkbox_recyclerview_ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.recycler_checkbox);
            checkBox_EditText=itemView.findViewById(R.id.checkbox_edittext);
        }
    }

    public Checkbox_recyclerview_adapter(ArrayList<Checkbox_recyclerview_items> cri_List){
        cri_LIST=cri_List;
    }
    @NonNull
    @Override
    public Checkbox_recyclerview_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_recyclerview_item,parent,false);
        Checkbox_recyclerview_ViewHolder crvh=new Checkbox_recyclerview_ViewHolder(v);
        return crvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Checkbox_recyclerview_ViewHolder holder, int position) {
        Checkbox_recyclerview_items currentItem=cri_LIST.get(position);

        holder.checkBox.setChecked(currentItem.get_checkbox_state());
        holder.checkBox_EditText.setText(currentItem.get_checkbox_edittext());
    }

    @Override
    public int getItemCount() {
        return cri_LIST.size();
    }
}

package vn.edu.uit.noteapp.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.noteapp.Checkbox_recyclerview_items;
import vn.edu.uit.noteapp.R;

public class Checkbox_recyclerview_adapter extends RecyclerView.Adapter<Checkbox_recyclerview_adapter.Checkbox_recyclerview_ViewHolder> {
    private  ArrayList<Checkbox_recyclerview_items> cri_LIST;
    public  class Checkbox_recyclerview_ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public EditText checkBox_EditText;
        public CustomEditTextListener mCustomEditTextListener;
        public Checkbox_recyclerview_ViewHolder(@NonNull View itemView,CustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.recycler_checkbox);
            checkBox_EditText=itemView.findViewById(R.id.checkbox_edittext);
            mCustomEditTextListener=myCustomEditTextListener;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        cri_LIST.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }


                }
            });
        }
        void enableTextWatcher() {
            checkBox_EditText.addTextChangedListener(mCustomEditTextListener);
        }

        void disableTextWatcher() {
            checkBox_EditText.removeTextChangedListener(mCustomEditTextListener);
        }
    }

    public Checkbox_recyclerview_adapter(ArrayList<Checkbox_recyclerview_items> cri_List){
        this.cri_LIST=cri_List;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull  Checkbox_recyclerview_adapter.Checkbox_recyclerview_ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ((Checkbox_recyclerview_ViewHolder) holder).enableTextWatcher();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull  Checkbox_recyclerview_adapter.Checkbox_recyclerview_ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((Checkbox_recyclerview_ViewHolder) holder).disableTextWatcher();
    }

    @NonNull
    @Override
    public Checkbox_recyclerview_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_recyclerview_item,parent,false);
        Checkbox_recyclerview_ViewHolder crvh=new Checkbox_recyclerview_ViewHolder(v,new CustomEditTextListener());
        return crvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Checkbox_recyclerview_ViewHolder holder, int position) {
        Checkbox_recyclerview_items currentItem=cri_LIST.get(position);

        holder.checkBox.setChecked(currentItem.get_checkbox_state());
        holder.mCustomEditTextListener.updatePosition(position);
        holder.checkBox_EditText.setText(currentItem.get_checkbox_edittext());

    }

    @Override
    public int getItemCount() {
        return cri_LIST.size();
    }


    public ArrayList<Checkbox_recyclerview_items> getCri_LIST() {
        return cri_LIST;
    }




    private class CustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            cri_LIST.get(position).set_checkbox_edittext(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}

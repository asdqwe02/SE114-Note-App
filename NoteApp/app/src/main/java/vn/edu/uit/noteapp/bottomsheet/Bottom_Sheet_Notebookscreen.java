package vn.edu.uit.noteapp.bottomsheet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Notebookscreen_recyclerview_adapter;

public class Bottom_Sheet_Notebookscreen extends BottomSheetDialogFragment {
    private Notebookscreen_recyclerview_adapter adapter;
    private int position;
    private Button remove;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_notebookscreen_item,container,false);
        remove = v.findViewById(R.id.Delete_notebook);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(position);
                Bottom_Sheet_Notebookscreen.this.dismiss();
            }
        });
        return v;
    }
    public Bottom_Sheet_Notebookscreen(int position, Notebookscreen_recyclerview_adapter adapter)
    {
        this.position = position;
        this.adapter = adapter;
    }

}

package vn.edu.uit.noteapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.uit.noteapp.R;

public class Bottom_Sheet_Notebookscreen extends BottomSheetDialogFragment {
    private Recycler_Adapter adapter;
    private int position;
    private LinearLayout remove;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_notebookscreen,container,false);
        remove = v.findViewById(R.id.remove_layout_item_bottom);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(position);
                Bottom_Sheet_Notebookscreen.this.dismiss();
            }
        });
        return v;
    }
    public Bottom_Sheet_Notebookscreen(int position, Recycler_Adapter adapter)
    {
        this.position = position;
        this.adapter = adapter;
    }

}

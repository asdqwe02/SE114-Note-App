package vn.edu.uit.noteapp.bottomsheet;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.Notebookscreen_recyclerview_adapter;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;

public class Bottom_Sheet_Notebookscreen extends BottomSheetDialogFragment {
    private Notebookscreen_recyclerview_adapter adapter;
    ArrayList<Model_Item_Notebook_screen> item_model;
    private int position;
    private Button remove,rename;
    private NoteBookBottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_notebookscreen_item,container,false);
        remove = v.findViewById(R.id.Delete_notebook);
        rename = v.findViewById(R.id.Rename_notebook);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(position);
                mListener.onDeleteButtonClick(true);
//                adapter.deleteNotebook(position);
                Bottom_Sheet_Notebookscreen.this.dismiss();
                }
            });

        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.renameNoteBook(adapter.getItemAtPos(position),position,true);
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
    public interface NoteBookBottomSheetListener{
        void onDeleteButtonClick(Boolean deleted);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (NoteBookBottomSheetListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()
                    +"must implement BottomSheetListener");
        }
    }
}

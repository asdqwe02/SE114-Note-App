package vn.edu.uit.noteapp.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.adapter.BookmarkScreen_adapter;

public class Bookmark_bottomsheet extends BottomSheetDialogFragment {
    private BookmarkScreen_adapter adapter;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_bookmark, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    public Bookmark_bottomsheet (BookmarkScreen_adapter adapter, int pos){
        this.adapter = adapter;
        this.position = pos;
    }
}

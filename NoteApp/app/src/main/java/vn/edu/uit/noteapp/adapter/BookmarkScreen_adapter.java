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
import vn.edu.uit.noteapp.bottomsheet.Bookmark_bottomsheet;
import vn.edu.uit.noteapp.data.Data_model_bookmark;

public class BookmarkScreen_adapter extends RecyclerView.Adapter<BookmarkScreen_adapter.ViewHolder> {
    Context context;
    ArrayList<Data_model_bookmark> data;

    public BookmarkScreen_adapter(Context context, ArrayList<Data_model_bookmark> Data){
        this.context = context;
        this.data = Data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View bookmark_view = inflater.inflate(R.layout.bookmark_row, parent,false );
        ViewHolder viewHolder = new ViewHolder(bookmark_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data_model_bookmark dataModel = data.get(position);
        holder.threepoints_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bookmark_bottomsheet bookmark_bottomsheet = new Bookmark_bottomsheet(BookmarkScreen_adapter.this,position);
                bookmark_bottomsheet.show(((FragmentActivity)context).getSupportFragmentManager(),"example");
            }
        });
        holder.bookmark_name.setText(dataModel.getBookmark_Name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView bookmark_name;
        private ImageButton threepoints_ic;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            bookmark_name = itemView.findViewById(R.id.tv_bookmarkName);
            threepoints_ic = itemView.findViewById(R.id.btn_Threepoints);
        }
    }
}

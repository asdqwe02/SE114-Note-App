package vn.edu.uit.noteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.uit.noteapp.data.Reminder_item;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.entities.Note;
import vn.edu.uit.noteapp.entities.Reminders;

public class Reminder_adapter extends RecyclerView.Adapter<Reminder_adapter.ViewHolder> {
    Context context;
    ArrayList<Reminder_item> Reminder_items;

    /*--Add a test with note adapter--*/
    ArrayList<Note> noteList;

    public Reminder_adapter(Context ct, ArrayList<Note> noteslist){
        this.context = ct;
        //this.Reminder_items = items;
        this.noteList = noteslist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_time;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

        void setReminder (Reminders reminders) {
            tv_title.setText(reminders.getTitle());

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item_view = inflater.inflate(R.layout.reminder_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(item_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reminder_adapter.ViewHolder holder, int position) {
        Reminder_item item = Reminder_items.get(position);
        holder.tv_title.setText(item.getItem_name());
        holder.tv_date.setText(item.getiDate());
        holder.tv_time.setText(item.getiTime());
    }

    @Override
    public int getItemCount() {
        return Reminder_items.size();
    }

}

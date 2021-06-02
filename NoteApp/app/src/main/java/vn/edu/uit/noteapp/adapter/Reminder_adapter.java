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

public class Reminder_adapter extends RecyclerView.Adapter<Reminder_adapter.ViewHolder> {
    Context context;
    ArrayList<Reminder_item> Reminder_items;

    public Reminder_adapter(Context ct, ArrayList<Reminder_item> items){
        this.context = ct;
        this.Reminder_items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
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
        holder.tv_name.setText(item.getItem_name());
        holder.tv_date.setText(item.getiDate());
        holder.tv_time.setText(item.getiTime());
    }

    @Override
    public int getItemCount() {
        return Reminder_items.size();
    }

}

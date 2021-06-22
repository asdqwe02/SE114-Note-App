package vn.edu.uit.noteapp.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.edu.uit.noteapp.activity.MainActivity;
import vn.edu.uit.noteapp.activity.Note_screen;
import vn.edu.uit.noteapp.activity.Reminder_screen;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.entities.Note;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.listeners.NotesListener;
import vn.edu.uit.noteapp.util.ItemTouchHelperAdapter;
import vn.edu.uit.noteapp.util.MyItemTouchHelper;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes;
    NotesListener notesListener;
    private Timer timer = new Timer();
    private List<Note> notesSource;
    public int title;
    private Context context;


    /**/
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public NoteAdapter(List<Note> notes, NotesListener noteListener, int title_screen, Context context) {
        this.notes = notes;
        this.notesListener = noteListener;
        notesSource = notes;
        this.title = title_screen;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
        holder.NoteContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesListener.onNoteClicked(notes.get(position), position);
                notes.get(position).getId();
            }
        });
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(notes.get(position).getId()));

        //demo delete note in main with swipe;
        // note: main and bookmark should have the same delete
        holder.LayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NoteAdapter.this.title == 0) {
                    Intent intent = new Intent(context, Note_screen.class);
                    intent.putExtra("isViewOrUpdate", true);
                    intent.putExtra("note", notes.get(position));
                    intent.putExtra("deleteWithSwipe", true);
                    context.startActivity(intent);
                }


                if (NoteAdapter.this.title == 3){
                    Intent intent = new Intent(context, Note_screen.class);
                    intent.putExtra("Delete Reminder", true);
                    intent.putExtra("note",notes.get(position));
                    intent.putExtra("SwipeToDelete", true);
                    context.startActivity(intent);
                }
            }

        });

        //
        holder.LayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Note_screen.class);
                intent.putExtra("Edit Reminder", true);
                intent.putExtra("note",notes.get(position));
                intent.putExtra("SwipeToEdit", true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tilteText, noteContentText, noteDateTimeText, rDate, rTime;
        LinearLayout NoteContainerLayout;
        RoundedImageView imageNoteContainer;

        /**/
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout LayoutDelete;
        LinearLayout LayoutEdit;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tilteText = itemView.findViewById(R.id.noteTitle);
            noteContentText = itemView.findViewById(R.id.noteTextContent);
            noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
            NoteContainerLayout = itemView.findViewById(R.id.note_container_layout);
            imageNoteContainer = itemView.findViewById(R.id.imageNoteContainer);
            rDate = itemView.findViewById(R.id.remindDate);
            rTime = itemView.findViewById(R.id.remindTime);

            /**/
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            LayoutDelete = itemView.findViewById(R.id.layout_delete);
            LayoutEdit = itemView.findViewById(R.id.layout_edit);
        }

        public void setNote(Note note) {
            tilteText.setText(note.getTitle());
            if (note.getNoteText().trim().isEmpty()) {
                noteContentText.setVisibility(itemView.GONE);
            } else if (note.getNoteText().trim().isEmpty() == false
                    && (NoteAdapter.this.title == 1 || NoteAdapter.this.title == 2)) {
                noteContentText.setVisibility(itemView.GONE);
            } else {
                noteContentText.setText(note.getNoteText());
            }
            noteDateTimeText.setText(note.getDateTime());
            /*----*/
            rDate.setText(note.getReminderDate());
            rTime.setText(note.getReminderTime());

            GradientDrawable gradientDrawable = (GradientDrawable) NoteContainerLayout.getBackground();
            if (note.getColor() != null) {
                String note_screen_color = note.getColor();
                if (note_screen_color.equals("#FAFAFA")
                        || note_screen_color.equals("#303030")
                        || note_screen_color.equals("#FFFFFF")) {
                    int currentNightMode = noteContentText.getContext().getResources().getConfiguration().uiMode
                            & Configuration.UI_MODE_NIGHT_MASK;
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            gradientDrawable.setColor(Color.parseColor("#FFFFFF"));
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            gradientDrawable.setColor(Color.parseColor("#303030"));
                            break;
                    }
                } else gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#00FFFFFF"));
            }

            /**/
            if (note.getImagePath() != null && NoteAdapter.this.title == 0) {
                imageNoteContainer.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNoteContainer.setVisibility(View.VISIBLE);
            } else if (note.getImagePath() != null && (note.isBookmark() == true || note.isReminder() == true)) {
                imageNoteContainer.setVisibility(View.GONE);
            } else {
                imageNoteContainer.setVisibility(View.GONE);
            }

            /**/
            if(NoteAdapter.this.title == 3){
                LayoutEdit.setVisibility(View.VISIBLE);
            } else {
                LayoutEdit.setVisibility(View.GONE);
            }
        }

    }

    public void searchNotes(final String searchKeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()) {
                    notes = notesSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notesSource) {
                        if (note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || note.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void cancelTimer() {
        if (timer == null)
            timer.cancel();
    }

    public List<Note> getNotesSource() {
        return notesSource;
    }

    public List<Note> getNotes() {
        return notes;
    }

}

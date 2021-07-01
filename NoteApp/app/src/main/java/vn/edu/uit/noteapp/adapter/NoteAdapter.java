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
import java.util.zip.Inflater;

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

        View view;

        if (NoteAdapter.this.title == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_row, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_container, parent, false);
        }

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
        if (NoteAdapter.this.title == 3) {
            //Reminder don't need to be able to edit the note contents
//            holder.ReminderContainerLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    notesListener.onNoteClicked(notes.get(position), position);
//                    //notes.get(position).getId();
//                }
//            });
            viewBinderHelper.setOpenOnlyOne(true);

            holder.LayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Note_screen.class);
                    intent.putExtra("Edit Reminder", true);
                    intent.putExtra("note", notes.get(position));
                    intent.putExtra("SwipeToEdit", true);
                    context.startActivity(intent);

                }
            });


        } else {
            holder.NoteContainerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notesListener.onNoteClicked(notes.get(position), position);
                    notes.get(position).getId();
                }
            });
            viewBinderHelper.setOpenOnlyOne(true);
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(notes.get(position).getId()));

        }

        // crash when click to reminder screen because of these function
        holder.LayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoteAdapter.this.title == 0
                        || NoteAdapter.this.title == 1
                        || NoteAdapter.this.title == 2) {
                    Intent intent = new Intent(context, Note_screen.class);
                    intent.putExtra("isViewOrUpdate", true);
                    intent.putExtra("note", notes.get(position));
                    intent.putExtra("deleteWithSwipe", true);
                    context.startActivity(intent);
                }


                if (NoteAdapter.this.title == 3) {
                    Intent intent = new Intent(context, Note_screen.class);
                    intent.putExtra("Delete Reminder", true);

                    intent.putExtra("note", notes.get(position));
                    intent.putExtra("SwipeToDelete", true);
                    context.startActivity(intent);
                }
            }
        });

        holder.LayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Note_screen.class);
                intent.putExtra("Edit Reminder", true);
                intent.putExtra("note", notes.get(position));
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
        TextView titleText, noteContentText, noteDateTimeText, rDate, rTime;
        LinearLayout NoteContainerLayout, LayoutDelete, LayoutEdit, ReminderContainerLayout;
        SwipeRevealLayout swipeRevealLayout;
        RoundedImageView imageNoteContainer;
        View rView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.noteTitle);
            rDate = itemView.findViewById(R.id.remindDate);
            rTime = itemView.findViewById(R.id.remindTime);

//            if(NoteAdapter.this.title == 0){
//                NoteContainerLayout = itemView.findViewById(R.id.note_container_layout);
//                imageNoteContainer = itemView.findViewById(R.id.imageNoteContainer);
//                swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
//                LayoutDelete = itemView.findViewById(R.id.layout_delete);
//                LayoutEdit = itemView.findViewById(R.id.layout_edit);
//                noteContentText = itemView.findViewById(R.id.noteTextContent);
//                noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
//            }

            if (NoteAdapter.this.title == 3) {
                ReminderContainerLayout = itemView.findViewById(R.id.reminder_container);
                swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
                rView = itemView.findViewById(R.id.view);
                LayoutDelete = itemView.findViewById(R.id.layout_delete);
                LayoutEdit = itemView.findViewById(R.id.layout_edit);
            } else {
                NoteContainerLayout = itemView.findViewById(R.id.note_container_layout);
                imageNoteContainer = itemView.findViewById(R.id.imageNoteContainer);
                swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
                LayoutDelete = itemView.findViewById(R.id.layout_delete);
                LayoutEdit = itemView.findViewById(R.id.layout_edit);
                noteContentText = itemView.findViewById(R.id.noteTextContent);
                noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
            }
        }


        public void setNote(Note note) {
            titleText.setText(note.getTitle());
            rDate.setText(note.getReminderDate());
            rTime.setText(note.getReminderTime());

            if (NoteAdapter.this.title == 3) {
                if (note.getColor().equals("#FAFAFA")
                        || note.getColor().equals("#FFFFFF")
                        || note.getColor().equals("#303030")) {
                    rView.setBackgroundColor(Color.parseColor("#3490DA"));
                } else rView.setBackgroundColor(Color.parseColor(note.getColor()));
            }
            //Home screen adapter & bookmark adapter
            else {
                if (note.getNoteText().trim().isEmpty()) {
                    noteContentText.setVisibility(View.GONE);
                } else {
                    noteContentText.setText(note.getNoteText());
                }
                noteDateTimeText.setText(note.getDateTime());


                //set color for each item
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

                //set image for the note which has image
                if (note.getImagePath() != null) {
                    imageNoteContainer.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                    imageNoteContainer.setVisibility(View.VISIBLE);
                } else {
                    imageNoteContainer.setVisibility(View.GONE);
                }
                LayoutEdit.setVisibility(View.GONE);

                if (NoteAdapter.this.title == 1 || NoteAdapter.this.title == 2) {
                    imageNoteContainer.setVisibility(View.GONE);
                    noteContentText.setVisibility(View.GONE);
                }
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

package vn.edu.uit.noteapp.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.listeners.NotesListener;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes;
    NotesListener notesListener;
    private Timer timer = new Timer();
    private List<Note> notesSource;

    public NoteAdapter(List<Note> notes, NotesListener noteListener) {
        this.notes = notes;
        this.notesListener = noteListener;
        notesSource = notes;
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

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tilteText, noteContentText, noteDateTimeText;
        LinearLayout NoteContainerLayout;
        RoundedImageView imageNoteContainer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tilteText = itemView.findViewById(R.id.noteTitle);
            noteContentText = itemView.findViewById(R.id.noteTextContent);
            noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
            NoteContainerLayout = itemView.findViewById(R.id.note_container_layout);
            imageNoteContainer = itemView.findViewById(R.id.imageNoteContainer);
        }

        public void setNote(Note note) {
            tilteText.setText(note.getTitle());
            if (note.getNoteText().trim().isEmpty()) {
                noteContentText.setVisibility(itemView.GONE);
            } else {
                noteContentText.setText(note.getNoteText());
            }
            noteDateTimeText.setText(note.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) NoteContainerLayout.getBackground();
            if (note.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#a9a9a9"));
            }

            if (note.getImagePath()!=null){
                imageNoteContainer.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNoteContainer.setVisibility(View.VISIBLE);
            } else {
                imageNoteContainer.setVisibility(View.GONE);
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
    public void cancelTimer(){
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

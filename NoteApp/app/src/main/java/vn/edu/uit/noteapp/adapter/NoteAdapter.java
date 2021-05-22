package vn.edu.uit.noteapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.listeners.NotesListener;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    List<Note> notes;
    NotesListener notesListener;

    public NoteAdapter(List<Note> notes, NotesListener noteListener) {
        this.notes = notes;
        this.notesListener=noteListener;
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
                notesListener.onNoteClicked(notes.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tilteText, noteContentText, noteDateTimeText;
        LinearLayout NoteContainerLayout;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tilteText = itemView.findViewById(R.id.noteTitle);
            noteContentText = itemView.findViewById(R.id.noteTextContent);
            noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
            NoteContainerLayout = itemView.findViewById(R.id.note_container_layout);
        }

        public void setNote(Note note) {
            tilteText.setText(note.getTitle());
            if (note.getNoteText().trim().isEmpty()){
                noteContentText.setVisibility(itemView.GONE);
            } else {
                noteContentText.setText(note.getNoteText());
            }
            noteDateTimeText.setText(note.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) NoteContainerLayout.getBackground();
            if (note.getColor()!=null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#a9a9a9"));
            }
        }
    }

}

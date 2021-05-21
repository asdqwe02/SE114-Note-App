package vn.edu.uit.noteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.uit.noteapp.Note;
import vn.edu.uit.noteapp.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
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


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tilteText = itemView.findViewById(R.id.noteTitle);
            noteContentText = itemView.findViewById(R.id.noteTextContent);
            noteDateTimeText = itemView.findViewById(R.id.noteDateTimeText);
        }

        public void setNote(Note note) {
            tilteText.setText(note.getTitle());
            if (note.getNoteText().trim().isEmpty()){
                noteContentText.setVisibility(itemView.GONE);
            } else {
                noteContentText.setText(note.getNoteText());
            }
            noteDateTimeText.setText(note.getDateTime());
        }
    }

}

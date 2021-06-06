package vn.edu.uit.noteapp.listeners;

import vn.edu.uit.noteapp.entities.Note;

public interface NotesListener {
    void onNoteClicked (Note note ,int position);
}

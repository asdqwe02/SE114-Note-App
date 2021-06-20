package vn.edu.uit.noteapp.util;

public interface ItemTouchHelperAdapter {

    void onItemMove (int fromPosition, int toPosition);

    void onItemSwiped(int position);

    long getNoteId (long noteID);
}

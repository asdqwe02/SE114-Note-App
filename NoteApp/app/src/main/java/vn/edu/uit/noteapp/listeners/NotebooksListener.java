package vn.edu.uit.noteapp.listeners;

import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;

public interface NotebooksListener {
    void OnNotebookClicked(Model_Item_Notebook_screen notebook, int position, boolean update);
}

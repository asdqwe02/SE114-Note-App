package vn.edu.uit.noteapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notebook")
public class Model_Item_Notebook_screen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private String id;

    @ColumnInfo(name = "NotebookName")
    private String item_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getItem_name(){
        return item_name;
    }
    public void setItem_name(String name)
    {
        this.item_name = name;
    }
    //
    public Model_Item_Notebook_screen(String name)
    {
        this.item_name = name;
    }
}
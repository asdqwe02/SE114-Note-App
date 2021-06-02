package vn.edu.uit.noteapp.data;

public class Model_Item_Notebook_screen {
    private String item_name;
    public Model_Item_Notebook_screen(){};
    public Model_Item_Notebook_screen(String name)
    {
        this.item_name = name;
    }
    public  String getItem_name(){
        return item_name;
    }
    public void setItem_name(String name)
    {
        this.item_name = name;
    }
}

package vn.edu.uit.noteapp.adapter;

public class Model_Item {
    private String item_name;
    public Model_Item(){};
    public Model_Item(String name)
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

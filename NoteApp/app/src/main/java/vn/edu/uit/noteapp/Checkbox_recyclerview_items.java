package vn.edu.uit.noteapp;

public class Checkbox_recyclerview_items {
    public String checkbox_editText;
    public boolean checkbox_state;

    public Checkbox_recyclerview_items(String text,boolean checkbox){
        checkbox_editText=text;
        checkbox_state=checkbox;
    }

    public boolean get_checkbox_state(){
        return  checkbox_state;
    }
    public String get_checkbox_edittext()
    {
        return checkbox_editText;
    }
}

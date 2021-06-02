package vn.edu.uit.noteapp.data;

import java.util.Date;

public class Reminder_item {
    private String iName;
    private String iDate;
    private String iTime;

    public Reminder_item(){}
    public Reminder_item(String name, String date, String time)
    {
        this.iName = name;
        this.iDate = date;
        this.iTime = time;
    }

    //Set and get name function
    public  String getItem_name(){
        return iName;
    }
    public String getiDate(){return iDate;}
    public String getiTime(){return iTime;}
    public void setItem_name(String name, String date, String time)
    {
        this.iName = name;
        this.iDate = date;
        this.iTime = time;
    }

}

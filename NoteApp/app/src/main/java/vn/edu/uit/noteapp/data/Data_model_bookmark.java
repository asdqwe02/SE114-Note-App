package vn.edu.uit.noteapp.data;

public class Data_model_bookmark {
    private String bookmark_Name;
    private int ic_Bookmark;
    private int ic_Threepoints;

    public Data_model_bookmark(String bookmarkName) {
        this.bookmark_Name = bookmarkName;
    }

    public String getBookmark_Name(){
        return bookmark_Name;
    }

    public void setBookmark_Name(String name){
        this.bookmark_Name = name;
    }

    public int getIc_Bookmark(){
        return ic_Bookmark;
    }

    public void setIc_Bookmark(int img_bookmark){
        this.ic_Bookmark = img_bookmark;
    }

    public int getIc_Threepoints(){
        return ic_Threepoints;
    }

    public void setIc_Threepoints(int img_threepoints){
        this.ic_Threepoints = img_threepoints;
    }
}

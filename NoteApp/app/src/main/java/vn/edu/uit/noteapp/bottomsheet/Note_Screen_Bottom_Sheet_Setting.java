package vn.edu.uit.noteapp.bottomsheet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.activity.MainActivity;
import vn.edu.uit.noteapp.activity.Note_screen;

public class Note_Screen_Bottom_Sheet_Setting extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;
    Button orangeButton;
    Button whiteButton;
    Button moveNoteToTrashButton;
    Button addReminder;
    boolean remindered;
    private int mDay,mMonth, mYear, mHour, mMinute;

    Button add_to_bookmark;
    Button add_to_notebook;
    boolean bookmarked;


    public static final String BOTTOM_SHEET_SHARE_PREFS="shareadPreferences";
    public static final String[] BOTTOM_SHEET_COlOR_BUTTON= new String[6];
    public boolean bottom_sheet_color_button[]=new boolean[6];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        BOTTOM_SHEET_COlOR_BUTTON[0] = "RedButton";
        BOTTOM_SHEET_COlOR_BUTTON[1] = "BlueButton";
        BOTTOM_SHEET_COlOR_BUTTON[2] = "GreenButton";
        BOTTOM_SHEET_COlOR_BUTTON[3] = "YellowButton";
        BOTTOM_SHEET_COlOR_BUTTON[4] = "OrangeButton";
        BOTTOM_SHEET_COlOR_BUTTON[5] = "WhiteButton";



        View v = inflater.inflate(R.layout.bottom_sheet_note_screen,container,false);
        redButton = v.findViewById(R.id.change_bg_red);
        blueButton = v.findViewById(R.id.change_bg_blue);
        greenButton = v.findViewById(R.id.change_bg_green);
        yellowButton = v.findViewById(R.id.change_bg_yellow);
        orangeButton = v.findViewById(R.id.change_bg_orange);
        whiteButton = v.findViewById(R.id.change_bg_white);
        moveNoteToTrashButton = v.findViewById(R.id.move_to_trash_button);
        addReminder = v.findViewById(R.id.btn_reminder);


        add_to_bookmark = v.findViewById(R.id.bookmark_note);
        add_to_notebook = v.findViewById(R.id.notebook_note);

        add_to_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Bookmark This Note");
                dismiss();
            }
        });

        add_to_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Move To Notebook");
                dismiss();
            }
        });

        moveNoteToTrashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Move To Trash");
                deleteSharePrefs();
                resetButtonBG();
                dismiss();
            }
        });

        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("White");
                CheckMarkColorButton(whiteButton);
                dismiss();
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Red");
                CheckMarkColorButton(redButton);
                dismiss();
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Blue");
                CheckMarkColorButton(greenButton);
                dismiss();
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Green");
                CheckMarkColorButton(greenButton);
                dismiss();
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Yellow");
                CheckMarkColorButton(yellowButton);
                dismiss();
            }
        });
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Orange");
                CheckMarkColorButton(orangeButton);
                dismiss();
            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Add reminder");
            }
        });

        remindered = this.getArguments().getBoolean("Remindered");
        if(remindered){
            addReminder.setText("        Remove reminder");
        }

        bookmarked=this.getArguments().getBoolean("Bookmarked");
        Log.d("BottomSheet_BookMark", "onCreateView: bookmark=" +bookmarked );
        if (bookmarked) {
            add_to_bookmark.setText("        Remove bookmark This Note");
            add_to_bookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_remove, 0, 0, 0);
        }
        sync_bottomSheet_colorButton_With_noteScreen();
        return v;
    }


    public void deleteSharePrefs()
    {
        SharedPreferences settings = this.getActivity()
                .getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    public void sync_bottomSheet_colorButton_With_noteScreen(){
        View v = ((Note_screen) getActivity()).getWindow().getDecorView();
        Drawable background = v.getBackground();
        int colorID = ((ColorDrawable) background).getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorID));
        switch (hexColor)
        {
            case "#EB5757": //red
                CheckMarkColorButton(redButton);
                break;
            case "#56CCF2": //blue
                CheckMarkColorButton(blueButton);
                break;
            case "#6FCF97": //green
                CheckMarkColorButton(greenButton);
                break;
            case "#F2C94C": //yellow
                CheckMarkColorButton(yellowButton);
                break;
            case "#F2994A": //orange
                CheckMarkColorButton(orangeButton);
                break;
            case "#FFFFFF": //white
                CheckMarkColorButton(whiteButton);
                break;
            default:
                break;
        }

    }


    public boolean check_DrawableLeft(Button button){
        Drawable[] drawable = button.getCompoundDrawables();
        if (drawable[0] != null)
            return true;
        return  false;
    }
    public void saveBottomSheetData()
    {
        SharedPreferences sharedPreferences = this.getActivity()
                .getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[0],check_DrawableLeft(redButton));
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[1],check_DrawableLeft(blueButton));
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[2],check_DrawableLeft(greenButton));
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[3],check_DrawableLeft(yellowButton));
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[4],check_DrawableLeft(orangeButton));
        editor.putBoolean(BOTTOM_SHEET_COlOR_BUTTON[5],check_DrawableLeft(whiteButton));
        editor.apply();
    }
    public void loadBottomSheetData(){
        SharedPreferences sharedPreferences = this.getActivity()
                .getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
        for (int i=0;i<6;i++){
            bottom_sheet_color_button[i]=sharedPreferences
                    .getBoolean(BOTTOM_SHEET_COlOR_BUTTON[i],false);
        }
    }
    public void updateBottomSheetData(){
        for (int i=0;i<6;i++) {
            if (bottom_sheet_color_button[i]==true)
                switch (i)
                {
                    case 0:
                        CheckMarkColorButton(redButton);
                        break;
                    case 1:
                        CheckMarkColorButton(blueButton);
                        break;
                    case 2:
                        CheckMarkColorButton(greenButton);
                        break;
                    case 3:
                        CheckMarkColorButton(yellowButton);
                        break;
                    case 4:
                        CheckMarkColorButton(orangeButton);
                        break;
                    case 5:
                        CheckMarkColorButton(whiteButton);
                        break;
                    default:
                        break;
                }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
    }

    //Donesn't work don't know why

    private void CheckMarkColorButton(Button button) {
        resetButtonBG();
        Resources res = getResources();
        Drawable  Button_bg_with_check_mark = getContext().getResources()
                .getDrawable(R.drawable.ic_check);
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check,0,0,0);
    }


    //Donesn't work don't know why
    private void resetButtonBG() {
        redButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        blueButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        greenButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        yellowButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        orangeButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        whiteButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }

    public interface BottomSheetListener{
        void OnBottomSheet_ButtonClicked(String text);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()
                    +"must implement BottomSheetListener");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        saveBottomSheetData();
        super.onDismiss(dialog);
    }
}



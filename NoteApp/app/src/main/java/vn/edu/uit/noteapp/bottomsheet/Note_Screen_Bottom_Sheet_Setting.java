package vn.edu.uit.noteapp.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.uit.noteapp.R;

public class Note_Screen_Bottom_Sheet_Setting  extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;
    Button orangeButton;
    Button whiteButton;
    Button moveNoteToTrashButton;

    //Bottom Sheet SharedPreferences
    public static final String BOTTOM_SHEET_SHARE_PREFS="shareadPreferences";
    public static final String[] BOTTOM_SHEET_COlOR_BUTTON= new String[6];
    public boolean bottom_sheet_color_button[]=new boolean[6];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



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


        //Delete button click event
        moveNoteToTrashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Move To Trash");
                deleteSharePrefs();
                resetButtonBG();
                dismiss();
            }
        });

        //white button click event
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("White");
                CheckMarkColorButton(whiteButton);
                dismiss();
            }
        });

        //red event click event
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Red");
                CheckMarkColorButton(redButton);
                dismiss();
            }
        });

        //blue button click button
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Blue");
                CheckMarkColorButton(greenButton);
                dismiss();
            }
        });

        //green button click event
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Green");
                CheckMarkColorButton(greenButton);
                dismiss();
            }
        });

        //yellow button click event
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Yellow");
                CheckMarkColorButton(yellowButton);
                dismiss();
            }
        });

        //orange button click event
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnBottomSheet_ButtonClicked("Orange");
                CheckMarkColorButton(orangeButton);
                dismiss();
            }
        });

        loadBottomSheetData();
        updateBottomSheetData();

        return v;
    }

    public void deleteSharePrefs()
    {
        SharedPreferences settings = this.getActivity().getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    public boolean check_DrawableLeft(Button button){
        Drawable[] drawable = button.getCompoundDrawables();
        if (drawable[0] != null)
            return true;
        return  false;
    }

    public void saveBottomSheetData()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(BOTTOM_SHEET_SHARE_PREFS,Context.MODE_PRIVATE);
        for (int i=0;i<6;i++){
            bottom_sheet_color_button[i]=sharedPreferences.getBoolean(BOTTOM_SHEET_COlOR_BUTTON[i],false);
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

    //Doesn't work don't know why

    private void CheckMarkColorButton(Button button) {
        resetButtonBG();
        Resources res = getResources();
        Drawable  Button_bg_with_check_mark = getContext().getResources().getDrawable(R.drawable.ic_check);
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check,0,0,0);
    }


    //Doesn't work don't know why
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



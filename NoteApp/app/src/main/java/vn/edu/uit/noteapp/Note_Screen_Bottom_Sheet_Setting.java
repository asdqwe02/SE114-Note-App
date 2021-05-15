package vn.edu.uit.noteapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Note_Screen_Bottom_Sheet_Setting  extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;
    Button orangeButton;
    Button whiteButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_setting,container,false);

        //rounded edge for bottom sheet note: this doesn't work
       // this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        redButton = v.findViewById(R.id.change_bg_red);
        blueButton = v.findViewById(R.id.change_bg_blue);
        greenButton = v.findViewById(R.id.change_bg_green);
        yellowButton = v.findViewById(R.id.change_bg_yellow);
        orangeButton = v.findViewById(R.id.change_bg_orange);
        whiteButton = v.findViewById(R.id.change_bg_white);

        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("White");
                outlineButton(whiteButton);
                dismiss();
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("Red");
                outlineButton(redButton);
                dismiss();
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("Blue");
                outlineButton(blueButton);
                dismiss();
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("Green");
                outlineButton(greenButton);
                dismiss();
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("Yellow");
                outlineButton(yellowButton);
                dismiss();
            }
        });
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnColorButtonClicked("Orange");
                outlineButton(orangeButton);
                dismiss();
            }
        });

        return v;
    }

    //Donesn't work don't know why
    private void outlineButton(Button button) {
        resetButtonBG();
        Resources res = getResources();
        Drawable Button_bg_with_outline = ResourcesCompat.getDrawable(res, R.drawable.roundedbutton_with_outline, null);
        button.setBackground(Button_bg_with_outline);
    }


    //Donesn't work don't know why
    private void resetButtonBG() {
        Resources res = getResources();
        Drawable defaultButton_bg = ResourcesCompat.getDrawable(res, R.drawable.roundedbutton, null);
        redButton.setBackground(defaultButton_bg);
        blueButton.setBackground(defaultButton_bg);
        greenButton.setBackground(defaultButton_bg);
        yellowButton.setBackground(defaultButton_bg);
        orangeButton.setBackground(defaultButton_bg);
    }

    public interface BottomSheetListener{
        void OnColorButtonClicked(String color);
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
}



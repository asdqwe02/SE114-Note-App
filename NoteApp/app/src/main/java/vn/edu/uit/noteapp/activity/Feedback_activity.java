package vn.edu.uit.noteapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;

import vn.edu.uit.noteapp.R;

public class Feedback_activity extends AppCompatActivity {

//    private ImageButton img_btn;
//    private RatingBar ratingBar;
//    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Set action bar
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Feedback");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setDisplayHomeAsUpEnabled(true);

        //change color of the button
        Button btn1 = (Button) findViewById(R.id.overall_service);
        OnClick(btn1);

        Button btn2 = (Button) findViewById(R.id.overall_quality);
        OnClick(btn2);

        Button btn3 = (Button) findViewById(R.id.customer_support);
        OnClick(btn3);

        Button btn4 = (Button) findViewById(R.id.animation);
        OnClick(btn4);

        Button btn5 = (Button) findViewById(R.id.Speed_efficiency);
        OnClick(btn5);

        Button btn6 = (Button) findViewById(R.id.user_interface);
        OnClick(btn6);

//        //change color of submit button
//        submit_btn = findViewById(R.id.btn_submit);
//        changeSubmitbtnColor(submit_btn);
    }

//    public void changeSubmitbtnColor(Button submit){
//        Button finalButton = submit;
//        if (ratingBar.isPressed()){
//            Drawable d = getResources().getDrawable(R.drawable.background_cliked_btn);
//            finalButton.setBackground(d);
//        }
//    }

    public void OnClick(Button button){
        int[] backGround = new int[]{ R.drawable.background_button, R.drawable.background_cliked_btn};
        Button finalButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d = getResources().getDrawable(R.drawable.background_cliked_btn);
                finalButton.setBackground(d);
            }
        });
    }
}
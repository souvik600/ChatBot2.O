package com.example.chatbot2o;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Splash_Screen_Activity extends AppCompatActivity {

    TextView textViewStart;
    ImageButton ibtnabout;
    TextView textViewExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textViewStart = findViewById(R.id.textViewStart);
        textViewExit = findViewById(R.id.textViewExit);
        ibtnabout = findViewById ( R.id.ibtnabout );
        //about
        ibtnabout.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent (Splash_Screen_Activity.this, About_Developer.class);
                startActivity ( intent );
            }
        } );

        //start & exit
        textViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textViewStart;
                textViewStart = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                startActivity(textViewStart);
            }
        });
        textViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
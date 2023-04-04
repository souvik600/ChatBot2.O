package com.example.chatbot2o;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class About_Developer extends AppCompatActivity {
    ImageButton back_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_about_developer );
        back_about = findViewById ( R.id.back_about );

        back_about.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
               finish ();
            }
        } );
    }
}
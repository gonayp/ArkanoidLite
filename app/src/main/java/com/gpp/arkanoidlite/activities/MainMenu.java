package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gpp.arkanoidlite.R;

public class MainMenu extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    Boolean audioState;
    ImageButton ibAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibAudio = findViewById(R.id.ibAudio);
        sharedPreferences = getSharedPreferences("my_pref",0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        if(audioState){
            ibAudio.setImageResource(R.drawable.audio_on);
        }else{
            ibAudio.setImageResource(R.drawable.audio_off);
        }
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainMenu.this, StartGame.class);
        startActivity(intent);

    }

    public void audioPref(View view) {
        if(audioState){
            audioState = false;
            ibAudio.setImageResource(R.drawable.audio_off);
        }else{
            audioState = true;
            ibAudio.setImageResource(R.drawable.audio_on);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("audioState",audioState);
        editor.apply();

    }
}
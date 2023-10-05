package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gpp.arkanoidlite.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean audioState, infiniteModeState;
    int levelState;
    ImageView ibAudio, ivModoInfinito, ivLevel;

    ConstraintLayout btn_level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_level = findViewById(R.id.btn_level);

        ivModoInfinito = findViewById(R.id.iv_infinite_mode);
        ivLevel = findViewById(R.id.iv_level);
        ibAudio = findViewById(R.id.iv_sound);

        sharedPreferences = getSharedPreferences("my_pref",0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        if(audioState){
            ibAudio.setImageResource(R.drawable.audio_on);
        }else{
            ibAudio.setImageResource(R.drawable.audio_off);
        }

        infiniteModeState = sharedPreferences.getBoolean("infiniteModeState", true);
        if(infiniteModeState){
            ivModoInfinito.setImageResource(R.drawable.check);
            btn_level.setVisibility(View.INVISIBLE);
        }else{
            ivModoInfinito.setImageResource(R.drawable.empty);
            btn_level.setVisibility(View.VISIBLE);
        }

        levelState = sharedPreferences.getInt("levelState", 1);
        switch (levelState){
            case 1:
                ivLevel.setImageResource(R.drawable.hud_1);
                break;
            case 2:
                ivLevel.setImageResource(R.drawable.hud_2);
                break;
            case 3:
                ivLevel.setImageResource(R.drawable.hud_3);
                break;
            case 4:
                ivLevel.setImageResource(R.drawable.hud_4);
                break;
            case 5:
                ivLevel.setImageResource(R.drawable.hud_5);
                break;
            case 6:
                ivLevel.setImageResource(R.drawable.hud_6);
                break;
            case 7:
                ivLevel.setImageResource(R.drawable.hud_7);
                break;
            case 8:
                ivLevel.setImageResource(R.drawable.hud_8);
                break;
            default:
                ivLevel.setImageResource(R.drawable.hud_1);
        }

    }

    public void onActionModoInfinito(View view) {
        if(infiniteModeState){
            infiniteModeState = false;
            ivModoInfinito.setImageResource(R.drawable.empty);
            btn_level.setVisibility(View.VISIBLE);
        }else{
            infiniteModeState = true;
            ivModoInfinito.setImageResource(R.drawable.check);
            btn_level.setVisibility(View.INVISIBLE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("infiniteModeState",infiniteModeState);
        editor.apply();
    }

    public void onActionDificultad(View view) {
        switch (levelState){
            case 1:
                levelState = 2;
                ivLevel.setImageResource(R.drawable.hud_2);
                break;
            case 2:
                levelState = 3;
                ivLevel.setImageResource(R.drawable.hud_3);
                break;
            case 3:
                levelState = 4;
                ivLevel.setImageResource(R.drawable.hud_4);
                break;
            case 4:
                levelState = 5;
                ivLevel.setImageResource(R.drawable.hud_5);
                break;
            case 5:
                levelState = 6;
                ivLevel.setImageResource(R.drawable.hud_6);
                break;
            case 6:
                levelState = 7;
                ivLevel.setImageResource(R.drawable.hud_7);
                break;
            case 7:
                levelState = 8;
                ivLevel.setImageResource(R.drawable.hud_8);
                break;
            case 8:
                levelState = 1;
                ivLevel.setImageResource(R.drawable.hud_1);
                break;
            default:
                levelState = 1;
                ivLevel.setImageResource(R.drawable.hud_1);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("levelState",levelState);
        editor.apply();
    }

    public void onActionSound(View view) {
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

    public void onActionBack(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainMenu.class);
        startActivity(intent);

    }
}
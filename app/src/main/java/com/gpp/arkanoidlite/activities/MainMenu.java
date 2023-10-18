package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gpp.arkanoidlite.Globales;
import com.gpp.arkanoidlite.R;

public class MainMenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onActionStartGame(View view) {
        Intent intent = new Intent(MainMenu.this, LevelMenuActivity.class);
        startActivity(intent);

    }

    public void onActionInfiniteGame(View view) {
        Globales.level = 0;
        Globales.mapaInicial = null;
        Intent intent = new Intent(MainMenu.this, StartGame.class);
        startActivity(intent);

    }

    public void onActionSettings(View view) {
        Intent intent = new Intent(MainMenu.this, SettingsActivity.class);
        startActivity(intent);

    }



}
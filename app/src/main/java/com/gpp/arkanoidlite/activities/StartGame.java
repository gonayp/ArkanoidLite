package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gpp.arkanoidlite.GameView;
import com.gpp.arkanoidlite.R;

public class StartGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);
    }

    public void startGame(View view) {

        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}
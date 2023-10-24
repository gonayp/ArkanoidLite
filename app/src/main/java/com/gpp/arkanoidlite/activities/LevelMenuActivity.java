package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.gpp.arkanoidlite.Globales;
import com.gpp.arkanoidlite.R;
import com.gpp.arkanoidlite.models.Brick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelMenuActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ConstraintLayout btnLevel02,btnLevel03,btnLevel04,btnLevel05,btnLevel06,btnLevel07,btnLevel08,btnLevel09,btnLevel10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        btnLevel02 = findViewById(R.id.btn_level02);
        btnLevel03 = findViewById(R.id.btn_level03);
        btnLevel04 = findViewById(R.id.btn_level04);
        btnLevel05 = findViewById(R.id.btn_level05);
        btnLevel06 = findViewById(R.id.btn_level06);
        btnLevel07 = findViewById(R.id.btn_level07);
        btnLevel08 = findViewById(R.id.btn_level08);
        btnLevel09 = findViewById(R.id.btn_level09);
        btnLevel10 = findViewById(R.id.btn_level10);
        btnLevel02.setEnabled(false);
        btnLevel03.setEnabled(false);
        btnLevel04.setEnabled(false);
        btnLevel05.setEnabled(false);
        btnLevel06.setEnabled(false);
        btnLevel07.setEnabled(false);
        btnLevel08.setEnabled(false);
        btnLevel09.setEnabled(false);
        btnLevel10.setEnabled(false);

        //Asignar audio y otras configuraciones segun preferencias
        sharedPreferences = getSharedPreferences("my_pref", 0);
        boolean level02 = sharedPreferences.getBoolean("level_2", false);
        if(level02) {
            btnLevel02.setEnabled(true);
            btnLevel02.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level03 = sharedPreferences.getBoolean("level_3", false);
        if(level03){
            btnLevel03.setEnabled(true);
            btnLevel03.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level04 = sharedPreferences.getBoolean("level_4", false);
        if(level04){
            btnLevel04.setEnabled(true);
            btnLevel04.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level05 = sharedPreferences.getBoolean("level_5", false);
        if(level05) {
            btnLevel05.setEnabled(true);
            btnLevel05.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level06 = sharedPreferences.getBoolean("level_6", false);
        if(level06){
            btnLevel06.setEnabled(true);
            btnLevel06.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level07 = sharedPreferences.getBoolean("level_7", false);
        if(level07) {
            btnLevel07.setEnabled(true);
            btnLevel07.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level08 = sharedPreferences.getBoolean("level_8", false);
        if(level08){
            btnLevel08.setEnabled(true);
            btnLevel08.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level09 = sharedPreferences.getBoolean("level_9", false);
        if(level09) {
            btnLevel09.setEnabled(true);
            btnLevel09.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }
        boolean level10 = sharedPreferences.getBoolean("level_10", false);
        if(level10) {
            btnLevel10.setEnabled(true);
            btnLevel10.setBackground(ContextCompat.getDrawable(this, R.drawable.buttonshape));
        }


    }

    public void onActionStartGameLevel1(View view) {

        crearNivel(1);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel2(View view) {

        crearNivel(2);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel3(View view) {

        crearNivel(3);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }

    public void onActionStartGameLevel4(View view) {

        crearNivel(4);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel5(View view) {

        crearNivel(5);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel6(View view) {

        crearNivel(6);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel7(View view) {

        crearNivel(7);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel8(View view) {

        crearNivel(8);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel9(View view) {

        crearNivel(9);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }
    public void onActionStartGameLevel10(View view) {

        crearNivel(10);
        Intent intent = new Intent(LevelMenuActivity.this, StartGame.class);
        startActivity(intent);

    }

    private void crearNivel(int p_level) {
        Globales.level = p_level;
        @SuppressLint("DefaultLocale") String name = "level_"+String.format("%04d",p_level);
        Globales.readAndExecuteLevel(getBaseContext(),name);

    }


    public void onActionBack(View view) {
        Intent intent = new Intent(LevelMenuActivity.this, MainMenu.class);
        startActivity(intent);

    }
}
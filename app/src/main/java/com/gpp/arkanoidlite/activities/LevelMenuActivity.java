package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);
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
        readAndExecuteLevel(getBaseContext(),name);

    }

















    private void readAndExecuteLevel(Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d("ERROR", "SQL script file name is empty");
            return;
        }

        AssetManager assetManager = ctx.getAssets();
        BufferedReader reader = null;

        try {
            InputStream is = assetManager.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            executeCreateLevel( reader);
        } catch (IOException e) {
            Log.e("ERROR", "IOException:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("ERROR", "IOException:", e);
                }
            }
        }

    }

    private void executeCreateLevel( BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        Globales.mapaInicial = new ArrayList<Brick>();

        while ((line = reader.readLine()) != null) {
            String[] bloque = line.split(",");
            Globales.mapaInicial.add(new Brick(Float.parseFloat(bloque[0]),Float.parseFloat(bloque[1]),null, Integer.parseInt(bloque[2]), 2, 0));
        }
    }

    public void onActionBack(View view) {
        Intent intent = new Intent(LevelMenuActivity.this, MainMenu.class);
        startActivity(intent);

    }
}
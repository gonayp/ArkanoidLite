package com.gpp.arkanoidlite.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gpp.arkanoidlite.Globales;
import com.gpp.arkanoidlite.R;

public class GameOver extends AppCompatActivity {
    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);
        int points = getIntent().getExtras().getInt("points");
        tvPoints.setText("" + points);
        int highest = puntuacionMasAlta();
        if (points > highest) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest_"+Globales.level, highest);
            editor.commit();
        }
        tvHighest.setText("" + highest);
    }

    private int puntuacionMasAlta() {
        sharedPreferences = getSharedPreferences("my_pref", 0);
        switch (Globales.level){
            case 0:
                return sharedPreferences.getInt("highest_0", 0);
            case 1:
                return sharedPreferences.getInt("highest_0", 0);
            case 2:
                return sharedPreferences.getInt("highest_2", 0);
            case 3:
                return sharedPreferences.getInt("highest_3", 0);
            case 4:
                return sharedPreferences.getInt("highest_4", 0);
            case 5:
                return sharedPreferences.getInt("highest_5", 0);
            case 6:
                return sharedPreferences.getInt("highest_6", 0);
            case 7:
                return sharedPreferences.getInt("highest_7", 0);
            case 8:
                return sharedPreferences.getInt("highest_8", 0);
            case 9:
                return sharedPreferences.getInt("highest_9", 0);
            case 10:
                return sharedPreferences.getInt("highest_10", 0);
            default:
                return sharedPreferences.getInt("highest_0", 0);
        }
        //int highest = sharedPreferences.getInt("highest", 0);
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}

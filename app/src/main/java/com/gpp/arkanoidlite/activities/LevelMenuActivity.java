package com.gpp.arkanoidlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gpp.arkanoidlite.Globales;
import com.gpp.arkanoidlite.R;
import com.gpp.arkanoidlite.models.Brick;

import java.util.ArrayList;
import java.util.List;

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
        switch (p_level){
            case 1:
                crearNivel1();
                break;
            case 2:
                crearNivel2();
                break;
            case 3:
                crearNivel3();
                break;
            case 4:
                crearNivel4();
                break;
            case 5:
                crearNivel5();
                break;
            case 6:
                crearNivel6();
                break;
            case 7:
                crearNivel7();
                break;
            case 8:
                crearNivel8();
                break;
            case 9:
                crearNivel9();
                break;
            case 10:
                crearNivel10();
                break;
            default:
                crearNivel1();
        }
    }

    private void crearNivel10() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(3,2,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(3,5,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,6,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(3,7,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,8,null, 7, 2, 0));

        Globales.mapaInicial.add(new Brick(7,2,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,5,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,6,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,8,null, 7, 2, 0));

        Globales.mapaInicial.add(new Brick(5,2,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,3,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(5,4,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,6,null, 8, 2, 0));
        Globales.mapaInicial.add(new Brick(5,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,8,null, 2, 2, 0));

        Globales.mapaInicial.add(new Brick(2,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(8,10,null, 2, 2, 0));

        Globales.mapaInicial.add(new Brick(2,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(8,11,null, 2, 2, 0));


    }

    private void crearNivel9() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(3,2,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,5,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,6,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,7,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(3,8,null, 2, 2, 0));

        Globales.mapaInicial.add(new Brick(7,2,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,6,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,8,null, 2, 2, 0));

        Globales.mapaInicial.add(new Brick(5,2,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,4,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,6,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,7,null, 7, 2, 0));
        Globales.mapaInicial.add(new Brick(5,8,null, 2, 2, 0));

    }

    private void crearNivel8() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(3,2,null, 6, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,6,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,8,null, 6, 2, 0));

        Globales.mapaInicial.add(new Brick(7,2,null, 6, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,6,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,8,null, 6, 2, 0));

        Globales.mapaInicial.add(new Brick(2,7,null, 6, 2, 0));
        Globales.mapaInicial.add(new Brick(4,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(8,7,null, 6, 2, 0));

        Globales.mapaInicial.add(new Brick(4,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(6,6,null, 3, 2, 0));

        Globales.mapaInicial.add(new Brick(6,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(4,6,null, 3, 2, 0));
    }

    private void crearNivel7() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(3,2,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(3,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,6,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(3,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,8,null, 5, 2, 0));

        Globales.mapaInicial.add(new Brick(7,2,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(7,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,6,null, 5, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,8,null, 5, 2, 0));

        Globales.mapaInicial.add(new Brick(2,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(8,3,null, 2, 2, 0));

        Globales.mapaInicial.add(new Brick(2,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(4,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(5,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(6,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(8,7,null, 4, 2, 0));


    }

    private void crearNivel6() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(3,2,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,5,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,6,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(3,8,null, 4, 2, 0));

        Globales.mapaInicial.add(new Brick(7,2,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(7,5,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(7,6,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 4, 2, 0));
        Globales.mapaInicial.add(new Brick(7,8,null, 4, 2, 0));
    }

    private void crearNivel5() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(5,2,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(2,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(4,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(5,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(6,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(8,3,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 3, 2, 0));

        Globales.mapaInicial.add(new Brick(4,2,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(2,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(4,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(5,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(6,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(7,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(8,4,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(6,5,null, 3, 2, 0));
    }

    private void crearNivel4() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(1,1,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(2,2,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,4,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,6,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(8,8,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(9,9,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(10,10,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(11,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(12,12,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(13,13,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(2,1,null, 3, 2, 0));
        Globales.mapaInicial.add(new Brick(3,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(5,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(7,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(8,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(9,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(10,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(11,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(12,1,null, 3, 2, 0));
    }

    private void crearNivel3() {

        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(1,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(2,2,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(4,4,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(5,5,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(6,6,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(7,7,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(8,8,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(9,9,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(10,10,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(11,11,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(12,12,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(13,13,null, 2, 2, 0));
    }

    private void crearNivel2() {
        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(1,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(2,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(3,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(4,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(5,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(6,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(7,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(8,1,null, 2, 2, 0));
        Globales.mapaInicial.add(new Brick(9,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(10,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(11,1,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(12,1,null, 1, 2, 0));

    }

    private void crearNivel1() {

        Globales.mapaInicial = new ArrayList<Brick>();
        Globales.mapaInicial.add(new Brick(5,2,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(2,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(3,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(4,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(5,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(6,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(7,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(8,3,null, 1, 2, 0));
        Globales.mapaInicial.add(new Brick(5,4,null, 1, 2, 0));

    }

    public void onActionBack(View view) {
        Intent intent = new Intent(LevelMenuActivity.this, MainMenu.class);
        startActivity(intent);

    }
}
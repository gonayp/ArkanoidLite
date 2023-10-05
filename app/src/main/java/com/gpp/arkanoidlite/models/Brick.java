package com.gpp.arkanoidlite.models;

import android.graphics.Bitmap;

public class Brick {

    float brickX, brickY;
    Bitmap brick;

    int type;

    int life;

    int item;

    public Brick(float brickX, float brickY, Bitmap brick, int type, int life, int item ) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.brick = brick;
        this.type = type;
        this.life = life;
        this.item = item;
    }

    public Brick(float brickX, float brickY, Bitmap brick) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.brick = brick;
        this.type = 1;
        this.life = 1;
        this.item = 0;
    }

    public float getBrickX() {
        return brickX;
    }

    public void setBrickX(float brickX) {
        this.brickX = brickX;
    }

    public float getBrickY() {
        return brickY;
    }

    public void setBrickY(float brickY) {
        this.brickY = brickY;
    }

    public Bitmap getBrick() {
        return brick;
    }

    public void setBrick(Bitmap brick) {
        this.brick = brick;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void reduceLife() {
        this.life--;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getBrickWidth() {
        return brick.getWidth();
    }

    public int getBrickHeight() {
        return brick.getHeight();
    }
}

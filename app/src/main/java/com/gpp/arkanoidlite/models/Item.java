package com.gpp.arkanoidlite.models;

import android.graphics.Bitmap;

public class Item {
    float itemX, itemY;
    Bitmap item;

    int type;

    public Item(float itemX, float itemY, Bitmap item, int type) {
        this.itemX = itemX;
        this.itemY = itemY;
        this.item = item;
        this.type = type;
    }

    public void incrementarY(int p_velocidad){
        this.itemY += p_velocidad;
    }

    public float getItemX() {
        return itemX;
    }

    public void setItemX(float itemX) {
        this.itemX = itemX;
    }

    public float getItemY() {
        return itemY;
    }

    public void setItemY(float itemY) {
        this.itemY = itemY;
    }

    public Bitmap getItem() {
        return item;
    }

    public void setItem(Bitmap item) {
        this.item = item;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItemWidth() {
        return item.getWidth();
    }

    public int getItemHeight() {
        return item.getHeight();
    }
}

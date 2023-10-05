package com.gpp.arkanoidlite.models;

import android.graphics.Bitmap;

public class Paddle {
    float paddleX, paddleY, oldPaddleX;
    Bitmap  paddle;

    public Paddle(float paddleX, float paddleY, Bitmap paddle) {
        this.paddleX = paddleX;
        this.paddleY = paddleY;
        this.paddle = paddle;
    }

    public float getPaddleX() {
        return paddleX;
    }

    public void setPaddleX(float paddleX) {
        this.paddleX = paddleX;
    }

    public float getPaddleY() {
        return paddleY;
    }

    public void setPaddleY(float paddleY) {
        this.paddleY = paddleY;
    }

    public float getOldPaddleX() {
        return oldPaddleX;
    }

    public void setOldPaddleX(float oldPaddleX) {
        this.oldPaddleX = oldPaddleX;
    }

    public Bitmap getPaddle() {
        return paddle;
    }

    public int getPaddleWidth() {
        return paddle.getWidth();
    }

    public int getPaddleHeight() {
        return paddle.getHeight();
    }
    public void setPaddle(Bitmap paddle) {
        this.paddle = paddle;
    }
}

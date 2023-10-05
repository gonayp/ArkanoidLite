package com.gpp.arkanoidlite.models;

import android.graphics.Bitmap;

public class Ball {

    float ballX, ballY;
    Bitmap ball;

    public Ball(float ballX, float ballY, Bitmap ball) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ball = ball;
    }

    public void incrementarX(int p_velocidad){
        this.ballX += p_velocidad;
    }

    public void incrementarY(int p_velocidad){
        this.ballY += p_velocidad;
    }

    public float getBallX() {
        return ballX;
    }

    public void setBallX(float ballX) {
        this.ballX = ballX;
    }

    public float getBallY() {
        return ballY;
    }

    public void setBallY(float ballY) {
        this.ballY = ballY;
    }

    public Bitmap getBall() {
        return ball;
    }

    public void setBall(Bitmap ball) {
        this.ball = ball;
    }

    public int getBallWidth() {
        return ball.getWidth();
    }

    public int getBallHeight() {
        return ball.getHeight();
    }
}

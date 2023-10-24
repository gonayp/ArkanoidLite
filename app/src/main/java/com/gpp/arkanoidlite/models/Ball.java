package com.gpp.arkanoidlite.models;

import android.graphics.Bitmap;

public class Ball {

    int velocidadInicialX =25, velocidadInicialY=-25;

    float ballX, ballY;
    Bitmap ball;

    Velocity velocity = new Velocity(0,0);

    boolean existe;

    public Ball(float ballX, float ballY, Bitmap ball) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.ball = ball;
        existe = false;
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

    public void aplicarVelocidad() {
        incrementarX(velocity.getX());
        incrementarY(velocity.getY());
    }

    public int getVelocidadX() {
        return velocity.getX();
    }

    public int getVelocidadY() {
        return velocity.getY();
    }

    public void aplicarVelocidadInicial() {
        velocity = new Velocity(velocidadInicialX, velocidadInicialY);//aplicar velocidad inicial si se hace click
    }


    public void setVelocidadY(int i) {
        velocity.setY(i);
    }

    public void setVelocidadX(int i) {
        velocity.setX(i);
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
}

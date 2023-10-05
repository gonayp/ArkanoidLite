package com.gpp.arkanoidlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.gpp.arkanoidlite.activities.GameOver;
import com.gpp.arkanoidlite.models.Ball;
import com.gpp.arkanoidlite.models.Brick;
import com.gpp.arkanoidlite.models.Paddle;
import com.gpp.arkanoidlite.models.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    Context context;

    int bricksCount = 10;

    int aceleration = 5;
    int velocidadInicialX =25, velocidadInicialY=-25;
    Velocity velocity = new Velocity(0,0);
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();

    float TEXT_SIZE = 120;
    float oldX;
    int points = 0;
    int life = 3;

    int dWidth, dHeight;
    MediaPlayer mpHit, mpMiss;
    Random random;
    SharedPreferences sharedPreferences;
    Boolean audioState;

    Paddle paddle;

    Ball ball;

    int posicionInicialX, posicionInicialY;

    List<Brick> bricks = new ArrayList<Brick>();

    public GameView(Context context) {
        super(context);
        this.context = context;

        mpHit = MediaPlayer.create(context, R.raw.hit);
        mpMiss = MediaPlayer.create(context, R.raw.miss);

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);

        //Sacar el tamaño de la pantalla
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;



        //Inicializar paddle
        Bitmap paddle_ = BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
        posicionInicialX = dWidth/2 - paddle_.getWidth() / 2;
        posicionInicialY = (dHeight *4)/5;
        paddle = new Paddle(posicionInicialX,
                posicionInicialY,
                paddle_);
        //Inicializar ball
        random = new Random();
        Bitmap ball_ = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(posicionInicialX + paddle.getPaddleWidth() / 3,
                paddle.getPaddleY() - paddle.getPaddleHeight() - ball_.getHeight()/3,
                ball_);

        //Inicializar bricks
        crearBricks();

        //Asignar audio segun preferencias
        sharedPreferences = context.getSharedPreferences("my_pref", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);


        handler = new Handler();

        runnable = this::invalidate;

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //cambiar color de fondo
        canvas.drawColor(Color.BLACK);
        //Añadir componente de velocidad a la pelota
        ball.incrementarX(velocity.getX());
        ball.incrementarY(velocity.getY());
        //Comprobar coliciones
        comprobarColisionesLaterales();
        comprobarColisionesInferiores();
        comprobarColisionesBricks();

        //Pintar elementos
        canvas.drawBitmap(ball.getBall(), ball.getBallX(), ball.getBallY(), null);
        canvas.drawBitmap(paddle.getPaddle(), paddle.getPaddleX(),paddle.getPaddleY(), null);
        for (Brick brick : bricks) {
            if(brick.getLife() > 0) {
                canvas.drawBitmap(brick.getBrick(), brick.getBrickX(), brick.getBrickY(), null);
            }
        }
        canvas.drawText("Puntos: "+points, 20, TEXT_SIZE, textPaint);

        if(life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if(life == 1){
            healthPaint.setColor(Color.RED);
        }

        //Pintar las barra superior segun la vida que quede
        canvas.drawRect(dWidth-200, 30, dWidth -200 + 60 * life, 80, healthPaint);

        //Rellamar al metodo onDraw para hacer el bucle de juego
        handler.postDelayed(runnable, UPDATE_MILLIS);


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if(touchY >=paddle.getPaddleY()){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                if(velocity.getX() == 0) {//Si todavia no se movio la pelota la movemos junto con la pal
                    velocity = new Velocity(velocidadInicialX, velocidadInicialY);//aplicar velocidad inicial si se hace click
                }
                oldX = event.getX();
                paddle.setOldPaddleX(paddle.getPaddleX());
            }
            if(action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPaddleX = paddle.getOldPaddleX() - shift;
                if (newPaddleX <= 0){
                   paddle.setPaddleX(0);
                } else if (newPaddleX >= dWidth - paddle.getPaddleWidth()) {
                   paddle.setPaddleX( dWidth - paddle.getPaddleWidth());
                }else{
                   paddle.setPaddleX(newPaddleX);
                }
                if(velocity.getX() == 0){//Si todavia no se movio la pelota la movemos junto con la pal
                    ball.setBallX(paddle.getPaddleX() + paddle.getPaddleWidth() / 2);
                }
            }
        }
        return true;
    }

    private void crearBricks() {
        for (int i= 0; i < bricksCount; i++) {
            Bitmap brick_ = BitmapFactory.decodeResource(getResources(), R.drawable.brick_grass01);
            int x = random.nextInt(dWidth)+2;
            int calculo = (int) (paddle.getPaddleY()-(3*ball.getBallHeight()));
            int y = random.nextInt(calculo);
            while(colicionesEntreBricks(x, y, brick_.getWidth(), brick_.getHeight())) {
                x = random.nextInt(dWidth)+2;
                y = random.nextInt(calculo);
            }
            bricks.add(new Brick(x, y, brick_));
        }
    }

    /**
     * Metodo para comprobar colision entre bloques al pintarlos
     * @param x posicion x del nuevo bloque
     * @param y posicion y del nuevo bloque
     * @param width ancho del nuevo bloque
     * @param height altura del nuevo bloque
     * @return true si hay colision
     */
    private boolean colicionesEntreBricks(int x, int y, float width, float height) {
        if(x+width >= dWidth){//colision con el muro izquierdo
            return true;
        }
        for (Brick brick : bricks) {//recorremos los bloques ya existentes
            if (((x + width) >= brick.getBrickX())
                    && (x <= brick.getBrickX() + brick.getBrickWidth())
                    && (y + height >= brick.getBrickY())
                    && (y  <= brick.getBrickY() + brick.getBrickHeight())) {
                return true;
            }
        }
        return false;
    }

    private void comprobarColisionesBricks() {

        //casos de contacto con un brick
        for (Brick brick : bricks) {
            if(brick.getLife() > 0) {//Solo comprueba colision con bloques vivos
                if (((ball.getBallX() + ball.getBallWidth()) >= brick.getBrickX())
                        && (ball.getBallX() <= brick.getBrickX() + brick.getBrickWidth())
                        && (ball.getBallY() + ball.getBallHeight() >= brick.getBrickY())
                        && (ball.getBallY()  <= brick.getBrickY() + brick.getBrickHeight())) {
                    if (mpHit != null && audioState) {//sonido
                        mpHit.start();
                    }
                    brick.reduceLife();
                    if (brick.getLife() == 0) {//Suma puntos solo si destruye el bloque
                        points++;
                        bricksCount--;
                    }
                    velocity.setX(velocity.getX() + aceleration/2);
                    velocity.setY((velocity.getY() + aceleration/2) * -1);

                }
            }
        }

    }

    private void comprobarColisionesInferiores() {
        //si la pelota cae abajo y no choca con la pala
        if(ball.getBallY() >paddle.getPaddleY() + paddle.getPaddleHeight()){
            //reiniciar posicion de la bola
            ball.setBallX(paddle.getPaddleX() + paddle.getPaddleWidth() / 3);
            ball.setBallY(paddle.getPaddleY() - paddle.getPaddleHeight() - ball.getBallHeight()/3);
            //sonido
            if(mpMiss != null && audioState){
                mpMiss.start();
            }
            //resetear velocidad
            velocity.setX(0);
            velocity.setY(0);
            life--;
            if(life == 0 || bricksCount <= 0){
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("points", points);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }
        //casos de contacto con la pala
        if( ((ball.getBallX() + ball.getBallWidth()) >= paddle.getPaddleX())
            && (ball.getBallX() <=paddle.getPaddleX()+ paddle.getPaddleWidth())
            && (ball.getBallY() + ball.getBallHeight() >=paddle.getPaddleY())
            && (ball.getBallY() + ball.getBallHeight() <=paddle.getPaddleY() + paddle.getPaddleHeight())){
                if(mpHit != null && audioState){//sonido
                    mpHit.start();
                }
                velocity.setX(xVelocity());
                velocity.setY((velocity.getY() + aceleration) * -1);
                //points++;
            if( bricksCount <= 0){
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("points", points);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }


    }

    private void comprobarColisionesLaterales() {
        if(ball.getBallX() >= dWidth -ball.getBallWidth() || ball.getBallX() <= 0){//si la pelota rebota de los lados
            velocity.setX(velocity.getX()*-1);//cambiar direccion de la pelota
        }
        if(ball.getBallY() <= 0){//Si la pelota rebota arriba
            velocity.setY(velocity.getY() *-1);//cambiar direccion de la pelota
        }
    }

    //Metodo para devolver uno de los posibles resultados de velocidad
    private int xVelocity() {
        int [] values = {-40,-35, -30, -25, -20, 20, 25, 30, 35, 40};
        int index = random.nextInt(10);
        return values[index];
    }
}

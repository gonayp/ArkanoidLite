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
import com.gpp.arkanoidlite.models.Item;
import com.gpp.arkanoidlite.models.Paddle;
import com.gpp.arkanoidlite.models.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    Context context;

    int bricksCount = 10;

    int aceleration = 4;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();

    float TEXT_SIZE = 80;
    float oldX;
    int points = 0;
    int life = 3;

    int dWidth, dHeight;
    MediaPlayer mpHit, mpMiss;
    Random random;
    SharedPreferences sharedPreferences;
    Boolean audioState;

    Paddle paddle;

    Ball ball_principal;
    List<Ball> extra_balls = new ArrayList<Ball>();

    int posicionInicialX, posicionInicialY;

    List<Brick> bricks = new ArrayList<Brick>();
    List<Item> items = new ArrayList<Item>();

    int dificultad = 1;

    boolean modoInfinito = true;

    boolean barrera_activa = false;

    Bitmap barrera;



    public GameView(Context context) {
        super(context);
        this.context = context;

        mpHit = MediaPlayer.create(context, R.raw.hit);
        mpMiss = MediaPlayer.create(context, R.raw.miss);

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);

        //Asignar audio y otras configuraciones segun preferencias
        sharedPreferences = context.getSharedPreferences("my_pref", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        modoInfinito = sharedPreferences.getBoolean("infiniteModeState", true);
        dificultad = sharedPreferences.getInt("levelState",1);
        if(modoInfinito) dificultad = 1;
        bricksCount += bricksCount*dificultad;

        //Sacar el tamaño de la pantalla
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        //Inicializar paddle
        Bitmap originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
        Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 200, 35, true);
        //Bitmap paddle_ = BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
        posicionInicialX = dWidth/2 - paddle_.getWidth() / 2;
        posicionInicialY = (dHeight *4)/5;
        paddle = new Paddle(posicionInicialX,
                posicionInicialY,
                paddle_);
        //Inicializar ball
        random = new Random();
        originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.ball_red);
        mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap ball_ = Bitmap.createScaledBitmap(mutableBitmap, 30, 30, true);
        ball_principal = new Ball(posicionInicialX + paddle.getPaddleWidth() / 3,
                paddle.getPaddleY() - paddle.getPaddleHeight() - 5 ,
                ball_);

        //Inicializar bricks
        crearBricks();
        //Inicializar extra balls
        crearExtraBalls();

        //Inicializar barrera
        originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.barrera);
        mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        barrera = Bitmap.createScaledBitmap(mutableBitmap, dWidth, 10, true);


        handler = new Handler();

        runnable = this::invalidate;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //cambiar color de fondo
        canvas.drawColor(Color.BLACK);
        //Añadir componente de velocidad a la pelota
        ball_principal.aplicarVelocidad();
        //Añadir componente de velocidad a las pelotas extra
        for (Ball ball: extra_balls){
            if(ball.isExiste()) ball.aplicarVelocidad();
        }
        //Añadir componente de velocidad a los items en pantalla
        for (Item item : items) {
            item.incrementarY(10);
        }
        //Comprobar coliciones
        comprobarColisionesLaterales(ball_principal);
        comprobarColisionesBarreraInferior(ball_principal);
        comprobarColisionesInferiores(ball_principal);
        comprobarColisionesConLaPala(ball_principal);
        comprobarColisionesBricks(ball_principal);
        comprobarColisionesItem();
        //Comprobar coliciones de pelotas extra
        comprobarColisionesExtraBalls();

        //Pintar elementos
        canvas.drawBitmap(ball_principal.getBall(), ball_principal.getBallX(), ball_principal.getBallY(), null);
        canvas.drawBitmap(paddle.getPaddle(), paddle.getPaddleX(),paddle.getPaddleY(), null);
        for (Brick brick : bricks) {
            if(brick.getLife() > 0) {
                canvas.drawBitmap(brick.getBrick(), brick.getBrickX(), brick.getBrickY(), null);
            }
        }
        for (Item item : items) {
            canvas.drawBitmap(item.getItem(), item.getItemX(), item.getItemY(), null);
        }
        for (Ball ball : extra_balls) {
            if(ball.isExiste())
                canvas.drawBitmap(ball.getBall(), ball.getBallX(), ball.getBallY(), null);
        }
        canvas.drawText("Puntos: "+points, 20,dHeight-TEXT_SIZE, textPaint);
        //canvas.drawText("Velocidad Y: "+ball_principal.getVelocidadY(), 20,dHeight-TEXT_SIZE*2, textPaint);
        if(barrera_activa)
            canvas.drawBitmap(barrera,0, paddle.getPaddleY()+paddle.getPaddleHeight(),null);

        if(life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if(life == 1){
            healthPaint.setColor(Color.RED);
        }else{
            healthPaint.setColor(Color.GREEN);
        }

        //Pintar las barra inferior segun la vida que quede
        canvas.drawRect(dWidth-200, dHeight-200, dWidth -200 + 60 * life,dHeight , healthPaint);

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
                if(ball_principal.getVelocidadX() == 0) {//Si todavia no se movio la pelota la movemos junto con la pal
                    ball_principal.aplicarVelocidadInicial();
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
                if(ball_principal.getVelocidadX() == 0){//Si todavia no se movio la pelota la movemos junto con la pala
                    ball_principal.setBallX(paddle.getPaddleX() + paddle.getPaddleWidth() / 2);
                }
            }
        }
        return true;
    }

    private void crearBricks() {
        if(Globales.mapaInicial != null){
            modoInfinito = false;
            bricksCount = 0;
            for (Brick brick : Globales.mapaInicial){
                crearBricks(brick);
            }
        }else {
            for (int i = 0; i < bricksCount; i++) {
                crearNewBrick();
            }
        }
    }

    private void crearExtraBalls() {

        for (int i=0; i <10; i++) {
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_extra);
            Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap ball_ = Bitmap.createScaledBitmap(mutableBitmap, 30, 30, true);
            int x = dWidth/2;
            int y = (int) paddle.getPaddleY() - ball_.getHeight() - 5;
            extra_balls.add(new Ball(x, y, ball_));
            ball_ = null;
        }
    }

    private void crearNewBrick() {
        int tipoBrick = calcularTipoBrickAleatorioSegunDificultad();
        Bitmap originalBitmap =  seleccionarBitmapSegunTipoBrick(tipoBrick);
        Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap brick_ = Bitmap.createScaledBitmap(mutableBitmap, 99, 99, true);
        int x = random.nextInt(dWidth)+2;
        int calculo = (int) (paddle.getPaddleY() -( dHeight -paddle.getPaddleY()));
        int y = random.nextInt(calculo);
        while(colicionesEntreBricks(x, y, brick_.getWidth(), brick_.getHeight())) {
            x = random.nextInt(dWidth)+2;
            y = random.nextInt(calculo);
        }
        bricks.add(new Brick(x, y, brick_,tipoBrick,tipoBrick,calcularItemAleatorio()));
        brick_ = null;
    }

    private void crearBricks(Brick brick) {
        int tipoBrick = brick.getType();
        Bitmap originalBitmap =  seleccionarBitmapSegunTipoBrick(tipoBrick);
        Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap brick_ = Bitmap.createScaledBitmap(mutableBitmap, 99, 99, true);
        int x = ((int)brick.getBrickX())*brick_.getWidth();
        int y = (int)brick.getBrickY()*brick_.getHeight();
        if(!colicionesEntreBricks(x, y, brick_.getWidth(), brick_.getHeight())) {
            bricks.add(new Brick(x, y, brick_, tipoBrick, tipoBrick, calcularItemAleatorio()));
            bricksCount++;
        }
        brick_ = null;

    }




    private int calcularTipoBrickAleatorioSegunDificultad() {
        return random.nextInt(dificultad)+1;
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
            if (((x + width) > brick.getBrickX())
                    && (x < brick.getBrickX() + brick.getBrickWidth())
                    && (y + height > brick.getBrickY())
                    && (y  < brick.getBrickY() + brick.getBrickHeight())) {
                return true;
            }
        }
        return false;
    }







    private void aplicarEfectoItem(int p_tipoItem) {

        switch (p_tipoItem){
            case 1://diamante
                points += 50;
                break;
            case 2://vida
                if(life < 3) life++;
                break;
            case 3://Mas velocidad
                if(ball_principal.getVelocidadY() > 0) ball_principal.setVelocidadY(50);
                else ball_principal.setVelocidadY(-50);
                break;
            case 4://Menos velocidad
                if(ball_principal.getVelocidadY() > 0) ball_principal.setVelocidadY(15);
                else ball_principal.setVelocidadY(-15);
                if(ball_principal.getVelocidadX() > 0) ball_principal.setVelocidadX(15);
                else ball_principal.setVelocidadX(-15);
                break;
            case 5://Paddle mas grande
                Bitmap originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle_big);
                Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 350, 35, true);
                paddle.setPaddle(paddle_);
                break;
            case 6://Paddle mas pequeño
                Bitmap originalBitmap_ =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle_small);
                Bitmap mutableBitmap_ = originalBitmap_.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap paddle__ = Bitmap.createScaledBitmap(mutableBitmap_, 100, 35, true);
                paddle.setPaddle(paddle__);
                break;
            case 7://mas pelotas
                createNewBalls();
                break;
            case 8://Fondo protegido
                barrera_activa = true;
                break;



        }
    }

    private void createNewBalls() {
        for(Ball ball: extra_balls){
            if(!ball.isExiste()){
                ball.setExiste(true);
                ball.setBallX(dWidth/2);
                ball.setBallY((int) paddle.getPaddleY() - ball.getBallHeight() - 5);
                ball.aplicarVelocidadInicial();
                ball.setVelocidadX(xVelocityAny());
                break;
            }
        }
    }



    private void crearItemBrickDestruido(int p_item_type, float p_x, float p_y) {
        Bitmap originalBitmap = seleccionarBitmapSegunTipoItem(p_item_type);
        if(p_item_type > 0) {
            if(p_item_type != 2){
                Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap item_ = Bitmap.createScaledBitmap(mutableBitmap, 200, 45, true);
                items.add(new Item(p_x, p_y, item_, p_item_type));
            }else{
                items.add(new Item(p_x, p_y, originalBitmap, p_item_type));
            }

        }
    }



    private void cambiarBitmapBrick(Brick brick) {
        Bitmap originalBitmap = null;

        switch (brick.getType()){
            case 2:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_04);
                break;
            case 3:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_06);
                break;
            case 4:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_08);
                break;
            case 5:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_10);
                break;
            case 6:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_12);
                break;
            case 7:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_14);
                break;
            case 8:
                if(brick.getLife() <= brick.getType()/2)
                    originalBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.brick_16);
                break;

        }
        if(originalBitmap != null) {
            Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap brick_ = Bitmap.createScaledBitmap(mutableBitmap, 99, 99, true);
            brick.setBrick(brick_);
        }
    }






    private void sumarPuntos(int tipo_brick) {
        points += tipo_brick * 10;
        if(modoInfinito){
            calcularDificultad();
        }
    }

    private void calcularDificultad() {
        if(points > 50) dificultad = 1;
        if(points > 550) dificultad = 2;
        if(points > 2000) dificultad = 3;
        if(points > 5000) dificultad = 4;
        if(points > 10000) dificultad = 5;
    }

    private void crearBrickModoInfinito() {
        if(modoInfinito){
            crearNewBrick();
            bricksCount++;
            if(dificultad > 4) {
                crearNewBrick();
                bricksCount++;
                if(dificultad > 7){
                    crearBricks();
                    bricksCount++;
                }
            }
        }
    }

    private Bitmap seleccionarBitmapSegunTipoBrick(int p_tipoBrick) {
        switch (p_tipoBrick){
            case 1:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_01);
            case 2:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_03);
            case 3:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_05);
            case 4:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_07);
            case 5:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_09);
            case 6:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_11);
            case 7:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_13);
            case 8:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_15);
            default:
                return BitmapFactory.decodeResource(getResources(), R.drawable.brick_17);
        }

    }

    private Bitmap seleccionarBitmapSegunTipoItem(int p_tipoItem) {
        switch (p_tipoItem){
            case 1:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_01);
            case 2:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_02);
            case 3:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_03);
            case 4:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_04);
            case 5:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_05);
            case 6:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_06);
            case 7:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_07);
            case 8:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_08);
            default:
                return BitmapFactory.decodeResource(getResources(), R.drawable.item_01);
        }

    }

    private void eliminarExtraBalls() {
        for(Ball ball: extra_balls){
            ball.setExiste(false);
        }
    }

    private void comprobarColisionesLaterales(Ball p_ball) {
        if(p_ball.getBallX() + p_ball.getBallWidth() >= dWidth-1 ){//si la pelota rebota del lado derecho
            p_ball.setBallX(dWidth - p_ball.getBallWidth()+1);
            p_ball.setVelocidadX(p_ball.getVelocidadX()*-1);//cambiar direccion de la pelota
        }
        if( p_ball.getBallX() <= 0){//si la pelota rebota del lado izquierdo
            p_ball.setBallX(1);
            p_ball.setVelocidadX(p_ball.getVelocidadX()*-1);//cambiar direccion de la pelota
        }
        if(p_ball.getBallY() - p_ball.getBallHeight() <= 0){//Si la pelota rebota arriba
            p_ball.setBallY(p_ball.getBallHeight()+1);
            p_ball.setVelocidadY(p_ball.getVelocidadY() *-1);//cambiar direccion de la pelota
        }
    }

    //si la pelota cae abajo, no choca con la pala y la barrera esta activa
    private void comprobarColisionesBarreraInferior(Ball p_ball){

        if(barrera_activa && p_ball.getBallY() >=  paddle.getPaddleY()+paddle.getPaddleHeight()){
            if(mpHit != null && audioState){//sonido
                mpHit.start();
            }
            //Cambiamos la velocidad de la pala en x y invertimos la de Y
            p_ball.setVelocidadX(xVelocity(p_ball.getVelocidadX()));
            p_ball.setVelocidadY((p_ball.getVelocidadY() + aceleration) * -1);
            barrera_activa = false;//desactivamos la barrera
            if( bricksCount <= 0){
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("points", points);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }
    }

    //casos de contacto con la pala
    private void comprobarColisionesConLaPala(Ball p_ball){

        if( ((p_ball.getBallX() + p_ball.getBallWidth()) >= paddle.getPaddleX())
                && (p_ball.getBallX() <=paddle.getPaddleX()+ paddle.getPaddleWidth())
                && (p_ball.getBallY() + p_ball.getBallHeight() >=paddle.getPaddleY())
                && (p_ball.getBallY()  <=paddle.getPaddleY() + paddle.getPaddleHeight())){
            if(mpHit != null && audioState){//sonido
                mpHit.start();
            }
            //Cambiamos la velocidad de la pala en x y invertimos la de Y
            p_ball.setVelocidadX(xVelocity(p_ball.getVelocidadX()));
            p_ball.setVelocidadY((p_ball.getVelocidadY() + aceleration) * -1);

            //TODO invertir eje x cuandop la pelota da en el borde del paddle

            comprobarCondicionesFinDePartida();

        }
    }

    //si la pelota cae abajo y no choca con la pala
    private void comprobarColisionesInferiores(Ball p_ball) {

        if(p_ball.getBallY() > dHeight - p_ball.getBallHeight()){
            //reiniciar posicion de la bola
            p_ball.setBallX(paddle.getPaddleX() + paddle.getPaddleWidth() / 3);
            p_ball.setBallY(paddle.getPaddleY() - paddle.getPaddleHeight() - p_ball.getBallHeight()/3);
            //sonido
            if(mpMiss != null && audioState){
                mpMiss.start();
            }
            //resetear velocidad
            p_ball.setVelocidadX(0);
            p_ball.setVelocidadY(0);
            life--;
            //resetear items
            items.clear();
            //resetear tamaño del paddle
            Bitmap originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
            Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 200, 35, true);
            paddle.setPaddle(paddle_);
            paddle_ = null;

            eliminarExtraBalls();

            comprobarCondicionesFinDePartida();
        }

    }




    private void comprobarColisionesInferioresExtraBall(Ball p_ball) {

        //si la pelota cae abajo y no choca con la pala
        if(p_ball.getBallY() > dHeight - p_ball.getBallHeight()){
            //eliminamos pelota
            p_ball.setExiste(false);
            //sonido
            if(mpMiss != null && audioState){
                mpMiss.start();
            }
            //resetear velocidad
            p_ball.aplicarVelocidadInicial();
            p_ball.setBallX(dWidth/2);
            p_ball.setBallY((int) paddle.getPaddleY() - p_ball.getBallHeight()- 5);

        }

    }


    private void comprobarColisionesBricks(Ball p_ball) {
        Brick bloqueDestruido = null;

        //casos de contacto con un brick
        for (Brick brick : bricks) {
            brick.desbloquear();
            if(brick.getLife() > 0 && !brick.bloqueado()) {//Solo comprueba colision con bloques vivos y no bloqueados
                if (((p_ball.getBallX() + p_ball.getBallWidth()) >= brick.getBrickX())
                        && (p_ball.getBallX() <= brick.getBrickX() + brick.getBrickWidth())
                        && (p_ball.getBallY() + p_ball.getBallHeight() >= brick.getBrickY())
                        && (p_ball.getBallY()  <= brick.getBrickY() + brick.getBrickHeight())) {
                    if (mpHit != null && audioState) {//sonido
                        mpHit.start();
                    }
                    brick.reduceLife();
                    brick.bloquear();
                    cambiarBitmapBrick(brick);
                    if (brick.getLife() == 0) {//Suma puntos solo si destruye el bloque
                        sumarPuntos(brick.getType());
                        crearItemBrickDestruido(brick.getItem(),brick.getBrickX(),brick.getBrickY());
                        bricksCount--;
                        bloqueDestruido = brick;
                    }
                    //Cambiar velocidad de la pelota
                    if(p_ball.getVelocidadX() > 0 && p_ball.getVelocidadY() > 0) {//positivo en eje X e Y
                        if((brick.getBrickX() - p_ball.getBallX()) > 0 ){//Si esta mas cerca de el lateral izq que del brick que del superior
                            reboteX(p_ball);
                        }else{
                            reboteY(p_ball);
                        }
                    }else if(p_ball.getVelocidadX() > 0 && p_ball.getVelocidadY() < 0){//positivo en eje X , negativo en Y
                        if ((brick.getBrickX() - p_ball.getBallX()) > 0){//Si esta mas cerca de el lateral izq que del brick que del inferior
                            reboteX(p_ball);
                        }else{
                            reboteY(p_ball);
                        }
                    }else if(p_ball.getVelocidadX() < 0 && p_ball.getVelocidadY() > 0){//negativo en eje X, positivo en Y
                        if(((p_ball.getBallX() + p_ball.getBallWidth()) - (brick.getBrickX() + brick.getBrickWidth()) ) > 0){//Si esta mas cerca de el lateral dere que del brick que del superior
                            reboteX(p_ball);
                        }else{
                            reboteY(p_ball);
                        }
                    }else if(p_ball.getVelocidadX() < 0 && p_ball.getVelocidadY() < 0){//negativo en eje X e Y
                        if(((p_ball.getBallX() + p_ball.getBallWidth()) - (brick.getBrickX() + brick.getBrickWidth()) ) > 0) {//Si esta mas cerca de el lateral dere que del brick que del inferior
                            reboteX(p_ball);
                        }else{
                            reboteY(p_ball);
                        }
                    }


                }
            }
        }

        if(bloqueDestruido != null){
            bricks.remove(bloqueDestruido);
            bloqueDestruido = null;
            crearBrickModoInfinito();
        }

    }

    private void comprobarColisionesItem() {
        Item item_a_borrar = null;
        for (Item item : items) {
            //si el item cae abajo y no choca con la pala
            if (item.getItemY() > dHeight-item.getItemHeight()) {
                item_a_borrar = item;
                //sonido
                if (mpMiss != null && audioState) {
                    mpMiss.start();
                }

            }
            //casos de contacto con la pala
            if (((item.getItemX() + item.getItemWidth()) >= paddle.getPaddleX())
                    && (item.getItemX() <= paddle.getPaddleX() + paddle.getPaddleWidth())
                    && (item.getItemY() + item.getItemHeight() >= paddle.getPaddleY())
                    && (item.getItemY()  <= paddle.getPaddleY() + paddle.getPaddleHeight())) {
                if (mpHit != null && audioState) {//sonido
                    mpHit.start();
                }
                item_a_borrar = item;
                aplicarEfectoItem(item.getType());
            }
        }
        //desaparece el item
        if(item_a_borrar != null)
            items.remove(item_a_borrar);

    }

    private void comprobarColisionesExtraBalls() {
        for(Ball ball: extra_balls){
            if(ball.isExiste()){
                comprobarColisionesLaterales(ball);
                comprobarColisionesBarreraInferior(ball);
                comprobarColisionesInferioresExtraBall(ball);
                comprobarColisionesConLaPala(ball);
                comprobarColisionesBricks(ball);
            }
        }

    }

    private void comprobarCondicionesFinDePartida() {
        //Comprobar condiciones de fin de partida
        if(life == 0 || bricksCount <= 0){
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            if(bricksCount <= 0) intent.putExtra("victoria",true);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }


    private void reboteY(Ball p_ball) {
        p_ball.setVelocidadX((p_ball.getVelocidadX() ) );
        //int velocidad = p_ball.getVelocidadY() + aceleration/2;
        //if(p_ball.getVelocidadY() < 0) velocidad = p_ball.getVelocidadY() - aceleration/2;
        p_ball.setVelocidadY(p_ball.getVelocidadY() * -1);
    }

    private void reboteX(Ball p_ball) {
        p_ball.setVelocidadX((p_ball.getVelocidadX()) * -1);
        //int velocidad = p_ball.getVelocidadY() + aceleration/2;
        //if(p_ball.getVelocidadY() < 0) velocidad = p_ball.getVelocidadY() - aceleration/2;
        //p_ball.setVelocidadY(velocidad);
    }


    //Metodo para devolver uno de los posibles resultados de velocidad
    private int xVelocity(int p_x_previus) {
        int [] values_positive = {15, 20, 25, 30, 35};
        int [] values_negative = {-35, -30, -25, -20, -15};
        int index = random.nextInt(5);
        if(p_x_previus > 0)
            return values_negative[index];
        return values_positive[index];
    }

    private int xVelocityAny() {
        int [] values = {15, 20, 25, 30, 35, -35, -30, -25, -20, -15};
        int index = random.nextInt(10);

        return values[index];
    }


    /**
     * Retorna un tipo de item aleatorio al crear el bloque
     * @return
     */
    private int calcularItemAleatorio() {

        int [] values =
               {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 2, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 2, 0, 0, 0, 0, 0, 8, 0, 0,
                1, 2, 0, 4, 5, 0, 0, 8, 0, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 0, 0};

        int index = random.nextInt(100);
        return values[index];

    }
}

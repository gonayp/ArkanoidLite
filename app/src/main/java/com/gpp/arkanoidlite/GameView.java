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
    int velocidadInicialX =25, velocidadInicialY=-25;
    Velocity velocity = new Velocity(0,0);
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

    Ball ball;

    int posicionInicialX, posicionInicialY;

    List<Brick> bricks = new ArrayList<Brick>();
    List<Item> items = new ArrayList<Item>();

    int dificultad = 1;

    boolean modoInfinito = true;



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
        Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 200, 30, true);
        //Bitmap paddle_ = BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
        posicionInicialX = dWidth/2 - paddle_.getWidth() / 2;
        posicionInicialY = (dHeight *4)/5;
        paddle = new Paddle(posicionInicialX,
                posicionInicialY,
                paddle_);
        //Inicializar ball
        random = new Random();
        originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap ball_ = Bitmap.createScaledBitmap(mutableBitmap, 30, 30, true);
        //Bitmap ball_ = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(posicionInicialX + paddle.getPaddleWidth() / 3,
                paddle.getPaddleY() - paddle.getPaddleHeight() - 5 ,
                ball_);

        //Inicializar bricks
        crearBricks();


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
        //Añadir componente de velocidad a los items en pantalla
        for (Item item : items) {
            item.incrementarY(10);
        }
        //Comprobar coliciones
        comprobarColisionesLaterales();
        comprobarColisionesInferiores();
        comprobarColisionesBricks();
        comprobarColisionesItem();

        //Pintar elementos
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.restart), dWidth, dHeight, null);
        canvas.drawBitmap(ball.getBall(), ball.getBallX(), ball.getBallY(), null);
        canvas.drawBitmap(paddle.getPaddle(), paddle.getPaddleX(),paddle.getPaddleY(), null);
        for (Brick brick : bricks) {
            if(brick.getLife() > 0) {
                canvas.drawBitmap(brick.getBrick(), brick.getBrickX(), brick.getBrickY(), null);
            }
        }
        for (Item item : items) {
            canvas.drawBitmap(item.getItem(), item.getItemX(), item.getItemY(), null);
        }
        canvas.drawText("Puntos: "+points, 20,dHeight-TEXT_SIZE, textPaint);

        if(life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if(life == 1){
            healthPaint.setColor(Color.RED);
        }else{
            healthPaint.setColor(Color.GREEN);
        }

        //Pintar las barra inferior segun la vida que quede
        canvas.drawRect(dWidth-200, dHeight-TEXT_SIZE, dWidth -200 + 60 * life,dHeight , healthPaint);

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

    private void comprobarColisionesBricks() {
        Brick bloqueDestruido = null;

        //casos de contacto con un brick
        for (Brick brick : bricks) {
            brick.desbloquear();
            if(brick.getLife() > 0 && !brick.bloqueado()) {//Solo comprueba colision con bloques vivos y no bloqueados
                if (((ball.getBallX() + ball.getBallWidth()) >= brick.getBrickX())
                        && (ball.getBallX() <= brick.getBrickX() + brick.getBrickWidth())
                        && (ball.getBallY() + ball.getBallHeight() >= brick.getBrickY())
                        && (ball.getBallY()  <= brick.getBrickY() + brick.getBrickHeight())) {
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
                    if(velocity.getX() > 0 && velocity.getY() > 0) {//positivo en eje X e Y
                        if((brick.getBrickX() - ball.getBallX()) > 0 ){//Si esta mas cerca de el lateral izq que del brick que del superior
                            reboteX();
                        }else{
                            reboteY();
                        }
                    }else if(velocity.getX() > 0 && velocity.getY() < 0){//positivo en eje X , negativo en Y
                            if ((brick.getBrickX() - ball.getBallX()) > 0){//Si esta mas cerca de el lateral izq que del brick que del inferior
                                reboteX();
                            }else{
                                reboteY();
                            }
                        }else if(velocity.getX() < 0 && velocity.getY() > 0){//negativo en eje X, positivo en Y
                            if(((ball.getBallX() + ball.getBallWidth()) - (brick.getBrickX() + brick.getBrickWidth()) ) > 0){//Si esta mas cerca de el lateral dere que del brick que del superior
                                reboteX();
                            }else{
                                reboteY();
                            }
                        }else if(velocity.getX() < 0 && velocity.getY() < 0){//negativo en eje X e Y
                            if(((ball.getBallX() + ball.getBallWidth()) - (brick.getBrickX() + brick.getBrickWidth()) ) > 0) {//Si esta mas cerca de el lateral dere que del brick que del inferior
                                reboteX();
                            }else{
                                reboteY();
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

    private void reboteY() {
        velocity.setX((velocity.getX() ) );
        velocity.setY((velocity.getY() + aceleration/2) * -1);
    }

    private void reboteX() {
        velocity.setX((velocity.getX()) * -1);
        velocity.setY((velocity.getY() + aceleration/2));
    }

    private void comprobarColisionesItem() {
        Item item_a_borrar = null;
        for (Item item : items) {
            //si el item cae abajo y no choca con la pala
            if (item.getItemY() > paddle.getPaddleY() + paddle.getPaddleHeight()) {
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

    private void aplicarEfectoItem(int p_tipoItem) {

        switch (p_tipoItem){
            case 1://diamante
                points += 10;
                break;
            case 2://vida
                if(life < 3) life++;
                break;
            case 3://Mas velocidad
                if(velocity.getY() > 0) velocity.setY(50);
                else velocity.setY(-50);
                break;
            case 4://Menos velocidad
                if(velocity.getY() > 0) velocity.setY(15);
                else velocity.setY(-15);
                if(velocity.getX() > 0) velocity.setX(15);
                else velocity.setX(-15);
                break;
            case 5://Paddle mas grande
                Bitmap originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle_big);
                Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 350, 30, true);
                paddle.setPaddle(paddle_);
                break;
            case 6://Paddle mas pequeño
                Bitmap originalBitmap_ =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle_small);
                Bitmap mutableBitmap_ = originalBitmap_.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap paddle__ = Bitmap.createScaledBitmap(mutableBitmap_, 100, 30, true);
                paddle.setPaddle(paddle__);
                break;



        }
    }

    /**
     * Retorna un tipo de item aleatorio al crear el bloque
     * @return
     */
    private int calcularItemAleatorio() {
        int [] values = {0,0, 0, 0, 0, 0, 0, 0, 0, 0,
                         0,0, 0, 0, 0, 0, 0, 0, 0, 0,
                         0,0, 0, 0, 0, 0, 0, 0, 0, 0,
                         1, 2, 3, 4, 5, 6, 7, 8, 1, 1};
        int index = random.nextInt(40);
        return values[index];

    }

    private void crearItemBrickDestruido(int p_item_type, float p_x, float p_y) {
        if(p_item_type > 0) {
            Bitmap item_ = seleccionarBitmapSegunTipoItem(p_item_type);
            items.add(new Item(p_x, p_y, item_, p_item_type));
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
            //resetear items
            items.clear();
            //resetear tamaño del paddle
            Bitmap originalBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.paddle);
            Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap paddle_ = Bitmap.createScaledBitmap(mutableBitmap, 200, 30, true);
            paddle.setPaddle(paddle_);
            //Comprobar condiciones d efin de partida
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
            && (ball.getBallY()  <=paddle.getPaddleY() + paddle.getPaddleHeight())){
                if(mpHit != null && audioState){//sonido
                    mpHit.start();
                }
                //Cambiamos la velocidad de la pala en x y invertimos la de Y
                velocity.setX(xVelocity(velocity.getX()));
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
        if(ball.getBallX() + ball.getBallWidth() >= dWidth-1 ){//si la pelota rebota del lado derecho
            ball.setBallX(dWidth - ball.getBallWidth()+1);
            velocity.setX(velocity.getX()*-1);//cambiar direccion de la pelota
        }
        if( ball.getBallX() <= 0){//si la pelota rebota del lado izquierdo
            ball.setBallX(1);
            velocity.setX(velocity.getX()*-1);//cambiar direccion de la pelota
        }
        if(ball.getBallY() - ball.getBallHeight() <= 0){//Si la pelota rebota arriba
            ball.setBallY(ball.getBallHeight()+1);
            velocity.setY(velocity.getY() *-1);//cambiar direccion de la pelota
        }
    }

    private void sumarPuntos(int tipo_brick) {
        points += tipo_brick;
        if(modoInfinito){
            calcularDificultad();
        }
    }

    private void calcularDificultad() {
        if(points > 10) dificultad = 1;
        if(points > 20) dificultad = 2;
        if(points > 40) dificultad = 3;
        if(points > 60) dificultad = 4;
        if(points > 100) dificultad = 5;
    }

    private void crearBrickModoInfinito() {
        if(modoInfinito){
            crearNewBrick();
            bricksCount++;
            if(dificultad > 5) {
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


    //Metodo para devolver uno de los posibles resultados de velocidad
    private int xVelocity(int p_x_previus) {
        int [] values_positive = {15, 20, 25, 30, 35};
        int [] values_negative = {-35, -30, -25, -20, -15};
        int index = random.nextInt(5);
        if(p_x_previus > 0)
            return values_negative[index];
        return values_positive[index];
    }
}

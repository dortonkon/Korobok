package com.example.koolguy.realshitanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by koolguy on 25.02.2018.
 */

public class Game_view extends View {

    private static int view_height,view_width;
    private Sprite sprite;
    private  Enemy enemy;
    int track;

    Timer t ;

    public Game_view(Context context)
    {
        super(context);
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.ttt);
        sprite = new Sprite(b,35,35);
        Bitmap b1 = BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
        enemy = new Enemy(b1,1000,1000);
        t = new Timer(Long.MAX_VALUE,150);
        new EnemyTimer(Long.MAX_VALUE,180).start();
        track=0;

        new Timer(Long.MAX_VALUE,285);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        view_height=h;
        view_width=w;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED );
        p.setTextSize(55);
        canvas.drawText(view_height+" "+view_width,200,200,p);
        canvas.drawRect(view_width*0,view_height*0,view_width,view_height/3 - 120,p);//up
        canvas.drawRect(view_width*0,view_height/3 * 2+120,view_width, view_height,p);// down
        p.setColor(Color.BLUE);
        canvas.drawRect(view_width/2+1,view_height/3 - 119,view_width,view_height/3 * 2+119,p);//right
        canvas.drawRect(view_width*0,view_height/3 - 119,view_width/2-1,view_height/3 * 2+119,p);//left
        Rect enemyrect = enemy.getBoundBox();
        Rect hero = sprite.getBoundBox();
        if(hero.intersect(enemyrect))
        {
            sprite.setY((int) Math.floor(Math.random() * 1000));
            sprite.setX((int) Math.floor(Math.random() * 1000));

            invalidate();

        }
        sprite.Draw(canvas);
        enemy.draw(canvas);
    }
    public static int getView_height(){return view_height;}
    public static int getView_width(){return view_width;}
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();
        int y = (int)event.getY();
        long time = event.getDownTime();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                    if(sprite.touchListener(x,y,true)){
                    t.start();invalidate();}
                break;
            case MotionEvent.ACTION_UP:
                sprite.touchListener(x,y,false);
                 t.cancel();
                break;




        }

        return true;
    }
    public boolean getBound()
    {



     return false;
    }
    class Timer extends CountDownTimer {
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onTick(long l) {
            sprite.update();

            invalidate();
            track++;

        }

        @Override
        public void onFinish() {

        }
    }
    class EnemyTimer extends CountDownTimer{
        public EnemyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            enemy.findHero(new Rect(sprite.getBoundBox()));
            invalidate();
        }

        @Override
        public void onFinish() {

        }
    }




}
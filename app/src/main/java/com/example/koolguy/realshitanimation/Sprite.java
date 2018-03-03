package com.example.koolguy.realshitanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.ArrayList;

/**
 * Created by koolguy on 25.02.2018.
 */

public class Sprite {
    private ArrayList<Rect> UP;
    private ArrayList<Rect> DOWN;
    private ArrayList<Rect> LEFT;       //в массивах прямоугольники,части картинки битмап
    private ArrayList<Rect> RIGHT;
    private Bitmap bitmap;
    private Rect destination;
    private int x,y;
    private int lastDirection;
    private int x_frame,y_frame;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public int getX_frame() {
        return x_frame;
    }

    public void setX_frame(int x_frame) {
        this.x_frame = x_frame;
    }

    public int getY_frame() {
        return y_frame;
    }

    public void setY_frame(int y_frame) {
        this.y_frame = y_frame;
    }

    public int getX() {
        return x;

    }

    public void setX(int x) {
        this.x = x;
    }

    private int currentframe,direction;        //директион определяет направление движения 0-4,ниже будет еще
    public Sprite(Bitmap bitmap,int x,int y)
    {
        direction = 4;
        lastDirection=4;
        this.bitmap=bitmap;
        UP = new ArrayList<Rect>();
        DOWN = new ArrayList<Rect>();
        LEFT = new ArrayList<Rect>();
        RIGHT = new ArrayList<Rect>();
        ReadBitmap();
        currentframe = 0;
        this.x = x;
        this.y = y;
        currentframe=0;


    }
    public void ReadBitmap()
    {
         x_frame = bitmap.getWidth()/4;
         y_frame =bitmap.getHeight()/4;
        for (int i = 0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                switch (i)
                {
                    case 0:DOWN.add(new Rect(x_frame*j,y_frame*i,x_frame*j+x_frame,y_frame*i+y_frame));break;  //i высота картинки j ширина
                    case 1:UP.add(new Rect(x_frame*j,y_frame*i,x_frame*j+x_frame,y_frame*i+y_frame));break;  //смотри изначальную картинку
                    case 2:LEFT.add(new Rect(x_frame*j,y_frame*i,x_frame*j+x_frame,y_frame*i+y_frame));break;
                    case 3:RIGHT.add(new Rect(x_frame*j,y_frame*i,x_frame*j+x_frame,y_frame*i+y_frame));break;

                }


            }

        }


    }

    public Rect getDestination() {
        return destination;
    }

    public void Draw(Canvas canvas)
    {
        Paint p =new Paint();
        p.setColor(Color.BLACK);
        destination= new Rect(x,y,x+x_frame,y+y_frame);
        switch (direction) {
            case 4:
                canvas.drawBitmap(bitmap, RIGHT.get(currentframe), destination, p);break;
            case 3:
                canvas.drawBitmap(bitmap,LEFT.get(currentframe),destination,p);break;
            case 2:                                                                                                //выбор какие спрайты рисовать
                canvas.drawBitmap(bitmap,DOWN.get(currentframe),destination,p);break;
            case 1:
                canvas.drawBitmap(bitmap,UP.get(currentframe),destination,p);break;
        }

        p.setTextSize(65);

        canvas.drawText(y_frame+" "+x_frame,55,100,p);
    }

    public boolean touchListener(int x,int y,boolean touch)                  //direction = 0(stop),1(up),2(down),3(left),4(right)
    { int view_width = Game_view.getView_width();
      int view_height = Game_view.getView_height();

      if(y>view_height/3 - 120&&y<view_height/3 * 2+119&&x>=view_width/2+1&&touch==true)
      {

          direction = 4;
          if(lastDirection!=direction) ChangeDirection();

          return true;
      }
      if (y>view_height/3 - 120&&y<view_height/3 * 2+119&&x<=view_width/2-1)//2.каждый if выбирает одну из кнопок, просто скопировал координаты из game_view
      {

          direction =3 ;
          if(lastDirection!=direction) ChangeDirection();

          return true;
      }
      if (y<=view_height/3 - 120)
      {

          direction = 1;
          if(lastDirection!=direction) ChangeDirection();
          return true;
      }
      if (y>=view_height/3 * 2+120)
      {

          direction = 2;
          if(lastDirection!=direction) ChangeDirection();
          return true;
      }
      else return false;



    }
    public Rect getBoundBox()
    {
        return new Rect(x+40,y+40,x+x_frame-40,y+y_frame-75);

    }
    public void update()
    {


      if(direction == 4 ) x+=30;
      if (direction==3) x-=30;
      if (direction==1)y-=30;               //происходит при каждом тике
      if(direction==2)y+=30;
      currentframe = (currentframe+1)%4;
    }
    public void ChangeDirection() //надо чтоб каждое движение начиналось с 1 спрайт(смотри картинку)
    {
        currentframe=0;
        lastDirection=direction;

    }


}

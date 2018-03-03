package com.example.koolguy.realshitanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.RequiresPermission;

import java.util.ArrayList;

/**
 * Created by koolguy on 02.03.2018.
 */

public class Enemy {
    ArrayList<Rect>motion;//0-3(down),4-7(dup),8-11(right),13-15(left)
    Bitmap sprite;

    private Rect destination;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getFrame_width() {

        return frame_width;
    }

    public void setFrame_width(int frame_width) {
        this.frame_width = frame_width;
    }

    public int getFrame_height() {
        return frame_height;
    }

    public void setFrame_height(int frame_height) {
        this.frame_height = frame_height;
    }

    public void setCurrentframe(int currentframe) {
        this.currentframe = currentframe;
    }

    private int x,y,frame_width,frame_height,currentframe,direction,padding;
    public Enemy(Bitmap bitmap, int x, int y) {
       sprite =bitmap;
       currentframe = 8;
       this.x=x;
       this.y=y;
       padding=20;
       frame_width=sprite.getWidth()/4;
       frame_height=sprite.getHeight()/4;
       motion=new ArrayList<Rect>();
       ReadBitmap();

    }
    public void ReadBitmap() {
        for(int i = 0;i<4;i++)
        {
            for(int j = 0 ; j<4;j++)
            {
             motion.add(new Rect(frame_width*j,frame_height*i,frame_width*j+frame_width,frame_height*i+frame_height));
            }

        }



    }
    public Rect getBoundBox()
    {
        return new Rect(x+40,y+40,x+frame_width-40,y+frame_height-75);

    }
    public void findHero(Rect hero)
    {
        int hero_x=hero.centerX();
        int hero_y=hero.centerY();
        if(hero_y>y)
        {
            if(hero_x>x)
            {
             x+=15;y+=15;
            }
            else
                {
                    x-=15;y+=15;
                }
        }
        else
            {
                if(hero_x>x)
                {
                    y-=15;x+=15;
                }
                else
                {
                    y-=15;x-=15;
                }
            }
    }
    public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        destination = new Rect(x,y,x+frame_width,y+frame_height);
        canvas.drawBitmap(sprite,motion.get(currentframe),destination,p);

    }
}

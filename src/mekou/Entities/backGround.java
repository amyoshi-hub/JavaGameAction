package mekou.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import mekou.GameEngine.GameObject;

public class backGround extends GameObject {

    public backGround(float x, float y, int w, int h) {
        super();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.z = -3;
        anim.load("idle", "mekou/img/backGround/idle", 1);

    }

    @Override
    public void draw(Graphics g) {
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){     
            g.drawImage(frame, (int)x, (int)y, width, height, null);
        }else{
            g.setColor(Color.BLUE);
            g.fillRect((int)x, (int)y, width, height);
            System.out.println("backGround image is null");
        }
    }
}
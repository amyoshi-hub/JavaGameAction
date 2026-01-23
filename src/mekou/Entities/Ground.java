package mekou.Entities;

import java.awt.Color;
import java.awt.Graphics;
import mekou.GameEngine.GameObject;

public class Ground extends GameObject {

    public Ground(float x, float y, int w, int h) {
        super();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.z = -1;
        anim.load("idle", "mekou/img/Ground/idle", 1);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, width, height);
        /*Image frame = anim.getCurrentFrame(tick);
        if (frame != null){     
            g.drawImage(frame, (int)x, (int)y, null);
        }else{
            System.out.println("Ground image is null");
        }
        */
    }
}
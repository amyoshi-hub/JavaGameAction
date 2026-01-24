package mekou.Entities;

import java.awt.*;
import mekou.GameEngine.GameObject;

public class Ground extends GameObject {

    public Ground(float x, float y, int w, int h) {
        super();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.z = -1;
        anim.load("idle", "mekou/img/Ground", 1);

    }

    @Override
    public void draw(Graphics g) {
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){     
            g.drawImage(frame, (int)x, (int)y, null);
        }else{
            g.setColor(Color.GREEN);
            g.fillRect((int)x, (int)y, width, height);
        }
    }
}
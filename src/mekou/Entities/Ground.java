package mekou.Entities;

import java.awt.*;
import mekou.GameEngine.GameObject;
import mekou.GameEngine.MapLoader;

public class Ground extends GameObject {

    private int sizeX, sizeY = 100;
    public Ground(float x, float y) {
        super();
        this.x = x;
        this.y = y;
        this.sizeX = MapLoader.getChipSize();
        this.sizeY = MapLoader.getChipSize();
        this.width = sizeX;
        this.height = sizeY;
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
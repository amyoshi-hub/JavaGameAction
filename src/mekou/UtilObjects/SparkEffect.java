package mekou.UtilObjects;
import mekou.GameObject;
import java.awt.*;

public class SparkEffect extends GameObject {
    private int life = 10;
    public SparkEffect(float x, float y) {
        this.x = x; this.y = y;
        this.useGravity = false;

        if (this.scene != null && this.scene.getPanel() instanceof mekou.Gra) {
            ((mekou.Gra)this.scene.getPanel()).setShack(10); 
        }
        anim.load("idle", "mekou/img/Enemy/idle", 1);

    }
    @Override
    public void update() {
        super.update();
        if (--life <= 0) setActive(false);
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int)y, 8, 8);
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null) {
            g.drawImage(frame, (int)x, (int)y, null);
        }
    }
}
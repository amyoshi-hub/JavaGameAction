import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class GameObject {
    protected float x, y;
    protected int z;
    protected int tick = 0;
    protected Animation anim;
    protected int width = 50, height = 50; // デフォルトサイズ（豆腐サイズ）

    public GameObject() {
        this.anim = new Animation();
    }

    // 毎フレームの計算
    public void update() {
        tick++;
    }

    // 描画処理（Sceneから呼ばれる）
    public void draw(Graphics g) {
        if (anim != null) {
            Image frame = anim.getCurrentFrame(tick);
            if (frame != null) {
                g.drawImage(frame, (int)x, (int)y, null);
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public int getZ() { return z; }
}
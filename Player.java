import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Player extends GameObject {

    private float vx = 0, vy = 0;
    private final float GRAVITY = 1.3f;
    private int ground = 300;
    private int deadLine = 400;

    public Player() {
        super();
        anim.load("idle", "img/player/idle", 1);
        anim.load("walk", "img/player/walk", 1);
        anim.load("jump", "img/player/jump", 1);
    }


    //gameに関してplayerのupdate
    @Override
    public void update() {
        super.update();
        x += vx;
        y += vy;

        if (y < ground) {
            vy += GRAVITY;
        } else {
            y = ground;
            vy = 0;
        }

        if (vy < 0) {
            anim.setState("jump");
        }
        if (vx != 0) {
            anim.setState("walk");
        } else {
            anim.setState("idle");
        }
        System.out.println("Player Position: (" + x + ", " + y + ")");
        if(y > deadLine){
            System.out.println("Game Over");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, 50, 50); // プレイヤーの当たり判定を四角で表示
        // timeInt（フレーム数）を渡して、今の絵をもらう
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){
            g.drawImage(frame, (int)x, (int)y, null);
        }
    }

    public void setVX(float vx) { this.vx = vx; }
    public void jump() {
        if (y >= 200) vy = -15; // 地面にいる時だけジャンプ
    }
}
// プレイヤーの中での使い方
public class Player extends GameObject {

    public Player() {
        super();
        anim.load("idle", "img/player/idle", 3);
        anim.load("walk", "img/player/walk", 6);
        anim.load("jump", "img/player/jump", 1);
    }

    @Override
    public void update() {
        super.update();
        x += vx;
        y += vy;

        if (y < 200) {
            vy += GRAVITY;
        } else {
            y = 200;
            vy = 0;
        }

        if (vx != 0) {
            anim.setState("walk");
        } else {
            anim.setState("idle");
        }
        
        if (velocityY < 0) {
            anim.setState("jump");
        }
    }

    @Override
    public void draw(Graphics g) {
        // timeInt（フレーム数）を渡して、今の絵をもらう
        Image current = anim.getCurrentFrame(timeInt);
        g.drawImage(current, x, y, null);
    }

    public void setVX(float vx) { this.vx = vx; }
    public void jump() {
        if (y >= 200) vy = -15; // 地面にいる時だけジャンプ
    }
}
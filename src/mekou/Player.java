package mekou;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import mekou.interfaces.*;

public class Player extends GameObject implements Damageable, Collider, Controllable {

    private int deadLine = 3000;
    private int health = 100;
    private boolean facingRight = false;

    public Player() {
        super();
        this.useGravity = true;
        anim.load("idle", "mekou/img/player/idle", 1);
        anim.load("walk", "mekou/img/player/walk", 1);
        anim.load("jump", "mekou/img/player/jump", 1);
    }

    //healthに関して
    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("Player took " + damage + " damage. Current health: " + health);
        if (health <= 0) {
            System.out.println("Player is dead.");
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    //gameに関してplayerのupdate
    @Override
    public void update() {
        isGrounded = false; // 毎フレーム開始時に地面にいないと仮定
        super.update();
        if(isGrounded) vy = 0;

        if (vy < 0) anim.setState("jump");
        if (vx != 0) anim.setState("walk");
        else anim.setState("idle");

        System.out.println("Player Position: (" + x + ", " + y + ")");
        //System.out.println("Player Health: " + health);
        //System.out.print(isGrounded);
        if(y > deadLine){
            //System.out.println("Game Over");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, width, height); // プレイヤーの当たり判定を四角で表示
        // timeInt（フレーム数）を渡して、今の絵をもらう
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){
            g.drawImage(frame, (int)x, (int)y, null);
        }
    }

    @Override
    public void setVX(float vx) { this.vx = vx; }

    @Override
    public void setVY(float vy) { this.vy = vy; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    @Override
    public void jump() {
        if (isGrounded) vy = -15; // 地面にいる時だけジャンプ
    }
 
    public void slide() {
    if (isGrounded) {
        this.height = 25; // 当たり判定を半分にする（スライディング！）
        this.vx = (facingRight) ? 10 : -10; // 勢いよく滑る
    }
}
}
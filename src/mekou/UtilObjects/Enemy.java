package mekou.UtilObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import mekou.GameObject;
import mekou.interfaces.*;

public class Enemy extends GameObject implements Damageable, Attack, Collider, Npc{
    private int health;

    public Enemy(int x, int y) {
        super();
        this.useGravity = true;
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.z = 0;
        this.health = 100;
        anim.load("idle", "mekou/img/Enemy/idle", 1);
    }

    public void update(){
        Move();
        super.update();
    }

private int actionTimer = 0;
private int currentDir = 0; // -1: 左, 0: 静止, 1: 右

@Override
    public void Move() {
        if (actionTimer <= 0) {
            // 数十フレームに一度だけ、次の行動を決める
            int random = new Random().nextInt(100);
            actionTimer = 30 + new Random().nextInt(30); // 30〜60フレーム継続
            
            if (random < 20) currentDir = 0; // 休憩
            else if (random < 60) currentDir = -1; // 左
            else currentDir = 1; // 右
            
            // ジャンプの抽選
            if (new Random().nextInt(10) > 8 && isGrounded) {
                vy = -12;
            }
        }
        if(!isGrounded){
            vy -= 0.5f;
        }

        // 決まった方向に力を加え続ける
        vx = currentDir * 2;
        actionTimer--;
    }

    @Override
    public void draw(Graphics g) {
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){     
            g.drawImage(frame, (int)x, (int)y, width, height, null);
        }else{
            g.setColor(Color.RED);
            g.fillRect((int)x, (int)y, width, height);
            System.out.println("Enemy image is null");
        }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        vx = -5; // ダメージを受けたら少し後退
        GameObject player = this.scene.getPlayer();
        if(player != null){
            this.vx = (player.getX() < this.x) ? 5 : -5; // プレイヤーの反対方向にノックバック
        }
        if (health < 0) health = 0;


        if (health == 0) {
            this.active = false;
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void attack() {
        System.out.println("Enemy attacks!");
    }
}

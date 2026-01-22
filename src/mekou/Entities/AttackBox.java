package mekou.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import mekou.GameEngine.GameObject;

public class AttackBox extends GameObject implements mekou.GameEngine.interfaces.AttackBoxInterface {
    private int lifeSpan = 10; // 10フレームだけ存在して消える
    private int damage = 20;

    public AttackBox(float x, float y, String dir) {
        super();
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.useGravity = false; // 攻撃判定は浮いててほしい
        
        // 向きによって位置を微調整
        if (dir.equals("LEFT")) this.x -= 40;
        anim.load("idle", "mekou/img/attack/idle", 1);

    }

    @Override
    public void update() {
        super.update();
        lifeSpan--;
        if (lifeSpan <= 0) {
            // Sceneから自分を消すフラグ（Engine側で削除処理が必要）
            this.active = false; 
        }
    }

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

    @Override
    public int getDamage() {
        return damage;
    }
}
package mekou.UtilObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import mekou.GameObject;
import mekou.interfaces.*;

public class Enemy extends GameObject implements Damageable, Attack, Collider{
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
        anim.load("idle", "mekou/img/Enemy/idle", 4);
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
        if (health < 0) health = 0;
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

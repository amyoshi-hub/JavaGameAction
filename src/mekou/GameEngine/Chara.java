package mekou.GameEngine;

import mekou.GameEngine.*;
import mekou.GameEngine.interfaces.*;

public abstract class Chara extends GameObject implements Damageable, Collider, Attack{
    protected int health;
    protected int maxHealth;
    protected boolean facingRight = true;

    @Override
    public void update(){
        isGrounded = false;
        super.update();

        if(this.y > this.scene.getPanel().getHeight()){
            onFalled();
        }
    }

    @Override
    public void updateAnimaion(){
        if(vx > 0) facingRight = true;
        else if (vx < 0) facingRight = false;

        updateState();
    }

    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("Player took " + damage + " damage. Current health: " + health);
        if (health <= 0) {
            //System.out.println("Player is dead.");
            // 死亡処理をここに追加
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setVX(float vx) { this.vx = vx; }

    public void setVY(float vy) { this.vy = vy; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    protected abstract void updateState();
    protected abstract void onFalled();
}

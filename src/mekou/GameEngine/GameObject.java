package mekou.GameEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class GameObject {
    protected boolean active = true;
    protected Scene scene;

    protected float x, y, vx, vy = 0;
    
    protected int z;
    protected int tick = 0;
    
    protected Animation anim;
    
    protected int width = 50, height = 50; // デフォルトサイズ（豆腐サイズ）
    
    private float aspectRatio = 1.0f;
    protected boolean useGravity = false;
    
    //gravity
    protected float gravityForce = 1.3f;
    protected boolean isGrounded = false;

    public GameObject() {
        this.anim = new Animation();
    }

    // 毎フレームの計算
    public void update() {
        isGrounded = false;
        tick++;
        if(useGravity) {
            applyGravity();
        }
        x += vx;
        y += vy;
    }

    // 描画処理（Sceneから呼ばれる）
    public void draw(Graphics g) {
        if (anim != null) {
            Image frame = anim.getCurrentFrame(tick);
            if (frame != null) {
                g.drawImage(frame, (int)x, (int)y, (int)(width * aspectRatio), (int)(height * aspectRatio), null);
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
    
    public int getX() { return (int)x; }
    public int getY() { return (int)y; }
    public int getZ() { return z; }

    public void applyGravity() {
        vy += gravityForce; // シンプルな重力効果
    }

    public void land(float groundY) {
        this.y = groundY - this.height; // 足元を地面の天面に合わせる
        this.isGrounded = true;
        this.vy = 0;
        System.out.println("Landed on the ground at y: " + this.y);
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
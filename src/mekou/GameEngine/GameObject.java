package mekou.GameEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public abstract class GameObject {
    GameObject parent = null;
    protected List<GameObject> children = new ArrayList<>();
    protected boolean active = true;
    protected Scene scene;

    protected float spawnX, spawnY;
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

    //フックメゾッド
    public void updateAnimation(){
        //継承させて下で処理させる
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
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setX(float x) { 
        this.x = x;
        if(parent != null){
            setX(this.x + parent.getX());
        }
    }
    public void setY(float y) { 
        this.y = y;
        if(parent != null){
            setY(parent.getY() + this.y);
        }
    }
    public void setZ(float z) {
         this.z = (int)z;
        if(parent != null){
            setZ(parent.getZ() + this.z); //zは奥行きの設定ということに注意　LWJGLの方との互換性を持ちたいだけ
        }        
    }

    public int getX() { return (int)x; }
    public int getY() { return (int)y; }
    public int getZ() { return z; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void applyGravity() {
        vy += gravityForce; // シンプルな重力効果
    }

    public void addChild(GameObject child) {
        child.parent = this;
        this.children.add(child);
    }
    public List<GameObject> getChildren() { return children; }

    public void land(float groundY) {
        this.y = groundY - this.height; // 足元を地面の天面に合わせる
        this.isGrounded = true;
        this.vy = 0;
        //System.out.println("Landed on the ground at y: " + this.y);
    }

    public void recordSpawnPoint() {
        this.spawnX = this.x;
        this.spawnY = this.y;
    }

    public void resetToSpawn() {
        this.x = spawnX;
        this.y = spawnY;
        this.active = true; // 死んでても復活
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
    public Scene getScene(){
        return this.scene;
    }
    public GameObject getParent(){
        return parent;
    }
}
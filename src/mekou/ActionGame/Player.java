package mekou.ActionGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import mekou.Entities.AttackBox;
import mekou.Entities.SparkEffect;
import mekou.GameEngine.*;
import mekou.GameEngine.interfaces.Controllable;

public class Player extends Chara implements Controllable{

    private static Player instance;
    private boolean CanAction = false;
    private String pendingDialogId = "";
    private int maxFallSpeed = 100;


    public Player() {
        super();
        instance = this;
        this.useGravity = true;
        this.health = 100;
        this.maxHealth = 100;
        anim.load("idle", "mekou/img/player/idle", 1);
        anim.load("walk", "mekou/img/player/walk", 1);
        anim.load("jump", "mekou/img/player/jump", 1);
    }

    @Override
    protected void updateState() {
        if (vy < 0) anim.setState("jump");
        else if (vx != 0) anim.setState("walk");
        else anim.setState("idle");
    }

    @Override
    public void attack() {
        float attackBoxWidth = 40;
        // 自分の座標を元に AttackBox を生み出す
        float ax = this.x + (facingRight ? this.width : this.x - attackBoxWidth);
        AttackBox ab = scene.createObject(new AttackBox(ax, this.y, facingRight ? "RIGHT" : "LEFT"));
        scene.createObject(new SparkEffect(ax, this.y));
    }

    //gameに関してplayerのupdate
    @Override
    public void update() {
        isGrounded = false; // 毎フレーム開始時に地面にいないと仮定
        this.height = 50; // 通常の高さに戻す

        super.update();
        updateAnimation();

        //System.out.println("Player Position: (" + x + ", " + y + ")");
        //System.out.println("Player Health: " + health);
        //System.out.print(isGrounded);
        if(this.scene != null && this.scene.getPanel() != null && this.y > this.scene.getPanel().getHeight()){
            //System.out.println("落下死しました"); //リス地を知っていたらリスポーン　知らなければやり直し
        }
    }

    public void updateAnimation(){
        if (vy < 0) anim.setState("jump");
        if (vx != 0) anim.setState("walk");
        else anim.setState("idle");
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, width, height); // プレイヤーの当たり判定を四角で表示
        // timeInt（フレーム数）を渡して、今の絵をもらう
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null){
            if(facingRight){
                g.drawImage(frame, (int)x, (int)y, width, height, null);
            }else{
                g.drawImage(frame, (int)x + width, (int)y, -width, height, null);
            }
        }
    }

    @Override
    public void jump() {
        if (isGrounded) vy = -15; // 地面にいる時だけジャンプ
    }
 
    public void slide() {
        if (isGrounded) {
            this.height = 25;
            if(vx > 0) this.vx = 10;
            else if (vx < 0) this.vx = -10;
            else this.vx = (facingRight) ? 10 : -10; // 勢いよく滑る
        }
    }

    public void downAttack() {
        System.out.println("Player performs down attack!");
        if(isGrounded){
            slide();
        }else{
            if(vy > maxFallSpeed) vy = maxFallSpeed;
            vy = 15; // 簡単な下降動作
        }
    }

    public void upperAction() {
        System.out.println("Player performs upper action!");
        vy = -10; // 簡単な上昇動作
    }

    @Override
    public void onCollide(GameObject other){

    }

    public void changeCanAction(){
        CanAction = !CanAction;
    }

    public boolean getCanAction(){
        return this.CanAction;
    }

    @Override
    protected void onFalled(){
        this.x = 100;
        this.y = 100;
        this.vy = 0;
    }

    public Scene getScene(){
        return this.scene;
    }
    @Override
    public String getPendingDialogId() { return pendingDialogId; }

    public void setPendingDialogId(String id) { this.pendingDialogId = id; }
    public static Player getInstance(){
        if(instance == null){
            instance = new Player();
        }
         return instance;
    }
}
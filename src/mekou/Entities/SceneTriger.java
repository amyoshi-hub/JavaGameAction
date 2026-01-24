package mekou.Entities;

import java.awt.Color;
import java.awt.Graphics;
import mekou.ActionGame.Player;
import mekou.GameEngine.GameLib.GameMode;
import mekou.GameEngine.GameObject;
import mekou.GameEngine.SceneManager;
import mekou.GameEngine.UI.DialogueManager;
import mekou.GameEngine.interfaces.Collider;

public class SceneTriger extends GameObject implements Collider {
    private String targetSceneName;
    private boolean isPlayerInside = false;
    private boolean hitThisFrame = false;

    public SceneTriger(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.z = 1000; // 最前面に描画
        this.targetSceneName = "Title";
    }

    @Override
    public void update() {
        // プレイヤーとの衝突判定は CollisionManager 側でやるか、
        // ここで自分からチェックしてもOK
        super.update();
        if(isPlayerInside && !hitThisFrame){
            onExit(Player.getInstance());
            isPlayerInside = false;
        }
        hitThisFrame = false;
    }

    @Override
    public void draw(Graphics g) {
        // 開発中だけ、どこにトリガーがあるか見えるようにする（デバッグ用）
        g.setColor(new Color(255, 0, 255, 100)); // 半透明の紫
        g.drawRect((int)x, (int)y, width, height);
    }

    @Override
    public void onCollide(GameObject other) {
        if (other instanceof Player) {
            hitThisFrame = true;
            
            // 1. フラグを立てる（未設定なら）
            if(!isPlayerInside){
                onEnter(Player.getInstance());
                isPlayerInside = true;
            }

            // 2. DIALOG 判定
            if(targetSceneName.startsWith("DIALOG:")){
                String dialogId = targetSceneName.substring("DIALOG:".length()).replace(")", "");
                // ここで開始せず、Player（または Movement）に ID を記憶させるだけ！
                Player.getInstance().setPendingDialogId(dialogId); 
                //System.out.println("dialog objectと衝突" + dialogId);
                return;
            }

            // 3. シーン遷移（こっちは触れたら即移動でOKならそのまま）
            SceneManager.getInstance().load(targetSceneName);
        }
    }

    public void setTargetSceneName(String newTargetSceneName){
        this.targetSceneName = newTargetSceneName;
    }

    public String getTarget() { return targetSceneName; }
        public GameMode getCurrentMode() {
        return SceneManager.getInstance().getCurrentGameMode();
    }

    public DialogueManager getDialogueManager() {
        return DialogueManager.getInstance(); 
    }

    private void onEnter(Player p) {
        System.out.println("Enter: 会話範囲に入った");
        if(!p.getCanAction()) p.changeCanAction();
    }

    private void onExit(Player p) {
        System.out.println("Exit: 会話範囲から出た");
        p.setPendingDialogId(null); // 離れたらIDを消す！
        if(p.getCanAction()) p.changeCanAction(); // アクション不可に戻す
    }
}
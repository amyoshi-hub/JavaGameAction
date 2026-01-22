package mekou.GameEngine;

import mekou.GameEngine.UI.DialogueManager;
import mekou.GameEngine.interfaces.Collider;
import java.util.HashMap;
import java.util.Map;

import java.awt.Graphics;
import java.awt.Color;

public class SceneTriger extends GameObject implements Collider {
    private String targetSceneName;

    public SceneTriger(int x, int y, String targetSceneName) {
        super();
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        this.z = 1000; // 最前面に描画
        this.targetSceneName = targetSceneName;
    }

    @Override
    public void update() {
        // プレイヤーとの衝突判定は CollisionManager 側でやるか、
        // ここで自分からチェックしてもOK
        super.update();
    }

    @Override
    public void draw(Graphics g) {
        // 開発中だけ、どこにトリガーがあるか見えるようにする（デバッグ用）
        g.setColor(new Color(255, 0, 255, 100)); // 半透明の紫
        g.drawRect((int)x, (int)y, width, height);
    }

    public void onCollide(GameObject other) {
        if (other instanceof mekou.ActionGame.Player) {
            // プレイヤーが触れたときの処理
            if(targetSceneName == null || targetSceneName.isEmpty()) {
                System.out.println("No target scene specified for SceneTriger.");
                return;
            }
            if(targetSceneName.startsWith("DIALOG:")){
                String dialogId = targetSceneName.substring("DIALOG:".length());
                System.out.println("Dialogue Triggered: " + dialogId);
                DialogueManager.getInstance().startDialogue(dialogId);
                return;
            }
            System.out.println("Scene Transition Triggered to: " + targetSceneName);
            SceneManager.getInstance().load(targetSceneName);
        }
    }

    public String getTarget() { return targetSceneName; }
        public SceneManager.GameMode getCurrentMode() {
        return SceneManager.getInstance().getCurrentGameMode();
    }

    public DialogueManager getDialogueManager() {
        return DialogueManager.getInstance(); 
    }
}
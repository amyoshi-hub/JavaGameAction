package mekou.GameEngine.UI;

import java.awt.Dialog;
import mekou.GameEngine.SceneManager;
import java.awt.*;
import mekou.GameEngine.SceneManager.GameMode;

public class UIManager {
    private HUD hud = new HUD();
    private DialogueManager dialogue = new DialogueManager();

    public void getUIManager() {
    
    }

    public void update() {
        SceneManager.GameMode mode = SceneManager.getInstance().getCurrentGameMode();
        switch(mode) {
            case STAGE:
                hud.update();
                break;
            case DIALOG:
                dialogue.update();
                break;
        }
    }

    public void draw(Graphics2D g2) {
        GameMode mode = SceneManager.getInstance().getCurrentGameMode();
        
        // カメラの影響を受けないように座標を 0,0 に固定して描画
        switch(mode) {
            case STAGE:
                hud.draw(g2);
                break;
            case DIALOG:
                dialogue.draw(g2);
                break;
        }
    }
}
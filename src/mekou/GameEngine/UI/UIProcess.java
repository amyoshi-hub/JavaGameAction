package mekou.GameEngine.UI;

import java.awt.*;
import mekou.GameEngine.GameLib.GameMode;
import mekou.GameEngine.SceneManager;


public class UIProcess {

    public static UIProcess instance;
    public void getUIManager() {
    
    }

    public void update() {
        GameMode mode = SceneManager.getInstance().getCurrentGameMode();
        switch(mode) {
            case STAGE :
                HUD.getInstance().update();
                break;
                case DIALOG :
            DialogueManager.getInstance().update();
                break;
            default :
                break;
        }
    }

    public void draw(Graphics2D g2) {
        GameMode mode = SceneManager.getInstance().getCurrentGameMode();
        
        // カメラの影響を受けないように座標を 0,0 に固定して描画
        switch(mode) {
            case STAGE :
                HUD.getInstance().draw(g2);
                break;
            case DIALOG :
                 //System.out.println("dialog mode display");
                DialogueManager.getInstance().draw(g2);
                break;
            default : 
                break;
        }
    }

    public static UIProcess getInstance(){
        if(instance == null){
            instance = new UIProcess();
        }
        return instance;
    }
}
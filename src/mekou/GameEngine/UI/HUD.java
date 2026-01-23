package mekou.GameEngine.UI;
import java.awt.Graphics2D;
import mekou.GameEngine.UI.UIInterface.UIElement;

public class HUD implements UIElement {

    public static HUD instance;

    public void draw(Graphics2D g2) {
        // HUD描画ロジックをここに実装
    }

    public void update() {
    
    }

    public static HUD getInstance(){
        if (instance == null) {
            instance = new HUD();
        }
        return instance;
    }
}

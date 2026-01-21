import java.awt.*;
import javax.swing.*;

class Gra extends JPanel {
    private Scene scene;

    public void setScene(Scene s) { this.scene = s; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scene != null) {
            scene.drawAll(g); // Sceneに登録された全オブジェクトを一括描画！
        }
    }
}
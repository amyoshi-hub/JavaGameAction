package mekou;

import java.awt.*;
import javax.swing.*;

public class Gra extends JPanel {
    private Scene scene;
    private GameObject cameraTarget;

    private int cameraShack = 0;

    public void setScene(Scene s) { this.scene = s; }

    public void setCameraTarget(GameObject target) {
        this.cameraTarget = target;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double WIDTH = 700.0;
        double HEIGHT = 400.0;
        
        double scale = Math.min(getWidth() / WIDTH, getHeight() / HEIGHT);
        
        g2.scale(scale, scale);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        if (scene != null) {
            int shakeX = 0, shakeY = 0;
            if(cameraTarget != null){
                if(cameraShack > 0){
                    shakeX = (int)(Math.random() * cameraShack * 2) - cameraShack;
                    shakeY = (int)(Math.random() * cameraShack * 2) - cameraShack;
                }
                g2.translate(-cameraTarget.getX() + 350 + shakeX, -50 + shakeY);
            scene.drawAll(g2); // Sceneに登録された全オブジェクトを一括描画！
            updateCamera();
        }
        if (cameraTarget != null) {
                g2.translate(-(350 - (int)cameraTarget.getX()), 0);
        }
        }
    }

    public void updateCamera() {
        if (cameraShack > 0) {
            cameraShack--;
        }
    }

    public void setShack(int amount){
        cameraShack = amount;
    }   
}
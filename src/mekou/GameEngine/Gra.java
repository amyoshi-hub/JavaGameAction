package mekou.GameEngine;

import java.awt.*;
import javax.swing.*;
import mekou.Entities.*;
import mekou.GameEngine.UI.*;

public class Gra extends JPanel {
    private Scene scene;
    private GameObject cameraTarget;
    private float camX;
    private float camY;

    private int cameraShack = 0;

    public void setScene(Scene s) { this.scene = s; }

    public void setCameraTarget(GameObject target) {
        this.cameraTarget = target;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double baseW = 700.0;
        double baseH = 400.0;
        double scale = Math.min(getWidth() / baseW, getHeight() / baseH);
        g2.scale(scale, scale);
        
        java.awt.geom.AffineTransform baseTransform = g2.getTransform();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (scene != null && cameraTarget != null) {
            updateCamera();
            
            // 1. 理想のカメラ位置（ターゲットを真ん中に置く位置）を計算
            float targetCamX = -cameraTarget.getX() + (float)(baseW / 2);
            float targetCamY = -cameraTarget.getY() + (float)(baseH / 2);

            // 2. 背景の範囲で制限をかける (Clamp)
            // 背景が 0 から始まっているなら、カメラのXは 0 以下（マイナス側）である必要がある
            if (backGround.getInstance() != null) {
                float bgX = backGround.getInstance().getX();
                float bgW = backGround.getInstance().getWidth();
                
                // 左端制限：カメラが背景の左端(bgX)より左に行かないようにする
                if (targetCamX > -bgX) targetCamX = -bgX;
                
                // 右端制限：カメラが背景の右端(bgX + bgW)を越えないようにする
                float minCamX = -(bgX + bgW - (float)baseW);
                if (targetCamX < minCamX) targetCamX = minCamX;
            }

            // シェイクを追加
            int shakeX = 0, shakeY = 0;
            if(cameraShack > 0){
                shakeX = (int)(Math.random() * cameraShack * 2) - cameraShack;
                shakeY = (int)(Math.random() * cameraShack * 2) - cameraShack;
            }

            // 3. 確定した座標で1回だけ移動！
            g2.translate(targetCamX + shakeX, targetCamY + shakeY);
            
            scene.drawAll(g2);
        }

        // UI描画
        g2.setTransform(baseTransform);
        UIProcess.getInstance().draw(g2);
    }
    
    public void updateCamera() {
        if (cameraShack > 0) {
            cameraShack--;
        }
    }

    public void setShack(int amount){
        cameraShack = amount;
    }

    public Scene getScene(){
        return this.scene;
    }
}
package mekou.UtilObjects;

import java.awt.*;
import mekou.GameObject;
import mekou.UtilObjects.UtilFunction.CameraShack;

public class SparkEffect extends GameObject {
    private int life = 10;
    private boolean hasShaken = false; // クラス直下に置く（フィールド変数）

    public SparkEffect(float x, float y) {
        super(); // 親の初期化を呼ぶ
        this.x = x; 
        this.y = y;
        this.useGravity = false;
        
        // アニメーションが必要ならここでロード
        anim.load("idle", "mekou/img/Enemy/idle", 1); 
    } // コンストラクタをここでしっかり閉じる！

    @Override
    public void update() {
        super.update();
        
        this.hasShaken = CameraShack.shakeCameraOnce(this.hasShaken, 5);

        if (--life <= 0) {
            setActive(false);
        }
    }

    @Override
    public void draw(Graphics g) {
        // デバッグ用に黄色い円を描画
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int)y, 8, 8);
        
        // 画像があれば描画
        Image frame = anim.getCurrentFrame(tick);
        if (frame != null) {
            g.drawImage(frame, (int)x, (int)y, null);
        }
    }
}
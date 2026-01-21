package mekou;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Animation {
    // 状態名（"walk"など）と、その画像の配列を紐付ける
    private Map<String, Image[]> animMap = new HashMap<>();
    private String currentState = "idle";
    private int frameWidth = 0, frameHeight = 0;
    private int speed = 5; // フレーム切り替え速度

    // 画像を読み込むメソッド（ディレクトリを指定して一括ロード）
    public void load(String stateName, String dirPath, int frameCount) {
        Image[] frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = dirPath + "/" + stateName + i + ".png";
            URL url = getClass().getClassLoader().getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                frames[i] = icon.getImage();
                if(i == 0){
                    this.frameWidth = icon.getIconWidth();
                    this.frameHeight = icon.getIconHeight();
                }
            }
        }
        animMap.put(stateName, frames);
    }

    // アニメーションを切り替え　walk->attackなど
    public void setState(String state) {
        if (animMap.containsKey(state)) {
            this.currentState = state;
        }
    }

    // 指定された時間(tick)に基づいて、現在のコマを返す
    public Image getCurrentFrame(int tick) {
        Image[] currentImages = animMap.get(currentState);
        if (currentImages == null) return null;
        return currentImages[(tick / speed) % currentImages.length];
    }
}
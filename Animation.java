import java.util.HashMap;
import java.util.Map;

public class Animation {
    // 状態名（"walk"など）と、その画像の配列を紐付ける
    private Map<String, Image[]> animMap = new HashMap<>();
    private String currentState = "idle";
    
    // 画像を読み込むメソッド（ディレクトリを指定して一括ロード）
    public void load(String stateName, String dirPath, int frameCount) {
        Image[] frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = dirPath + "/" + stateName + i + ".png";
            URL url = getClass().getClassLoader().getResource(path);
            if (url != null) {
                frames[i] = new ImageIcon(url).getImage();
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
        return currentImages[tick % currentImages.length];
    }
}
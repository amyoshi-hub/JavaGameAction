package mekou.Entities.UtilFunction;

import mekou.GameEngine.Gra;
import mekou.GameEngine.Scene;

public class CameraShack {
    private static Gra gamePanel; // 揺らす対象を保持しておく

    // 最初に一度だけ、エンジンの起動時にパネルを登録する
    public static void init(Gra panel) {
        gamePanel = panel;
    }

    // これをどこからでも呼ぶ！
    public static void trigger(int amount) {
        if (gamePanel != null) {
            gamePanel.setShack(amount);
        }
    }

    public static boolean shakeCameraOnce(boolean alreadyShaken, int amount) {
        if (!alreadyShaken) {
            trigger(amount); // 実際の揺らし処理
            return true;    // 「揺らしました」という印を返す
        }
        return true; // すでに揺れているならそのまま true を維持
    }
}
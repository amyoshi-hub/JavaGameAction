package mekou.GameEngine;

import java.io.*;
import java.nio.file.*;
import mekou.ActionGame.Player;
import mekou.Entities.*;

public class MapLoader {
    private final int CHIP_SIZE = 50; // 1ブロックのサイズ
    private Scene scene;
    private Player player;
    int MAX_HEIGHT_CHIPS = 8;

    public <T> MapLoader(Scene scene) {
        this.scene = scene;
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = Frame.getInstance().getStageStream(filePath);
            if (is == null){
                System.err.println("Map File not found:" + filePath);
                return;
            }
            String data;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                data = sb.toString();
            }
            // 改行で分割（各行がゲームの「X方向」の1列分になる）
            String[] columns = data.split("\n");

        for (int x = 0; x < columns.length; x++) {
            String column = columns[x];
            // 各行の中の「文字」が、ゲームの「Y方向（高さ）」になる
            for (int y = 0; y < column.length(); y++) {
                char chip = column.charAt(y);
                
                // MEKOUマッピング：配列のインデックスをそのまま座標に変換
                int px = x * CHIP_SIZE;
                int py = (MAX_HEIGHT_CHIPS - y - 1) * CHIP_SIZE;

                switch (chip) {
                    //特殊parse
                    case 'N': // SceneTriger
                    case 'D': // Decal
                        String sub = column.substring(y);
                        String target = extractTarget(sub);
                        
                        if (chip == 'N') scene.setSceneTriger(px, py, target);
                        else scene.createObject(new Decal(px, py, 0, target));
                        
                        // カッコの終わり ')' までインデックスを飛ばす
                        int closingBracketIndex = sub.indexOf(")");
                            if (closingBracketIndex != -1) {
                                y += closingBracketIndex; // カッコがある時だけ飛ばす
                            } else {
                                // カッコがない場合はエラーを出してその1文字だけ飛ばす
                                System.err.println("Map Error: Missing ')' at col " + x + " index " + y);
                        }
                        break;

                    case 'G':
                        scene.createObject(new Ground(px, py, CHIP_SIZE, CHIP_SIZE));
                        break;
                    case 'E':
                        scene.createObject(new Enemy(px, py));
                        break;
                    case 'P':
                        this.player = new Player();
                        this.player.setX(px);
                        this.player.setY(py);
                        scene.createObject(this.player);
                        break;
                    case ' ': 
                        break;
                }
            }
        } 
        }catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    private String extractTarget(String data) {
    int start = data.indexOf("(") + 1;
    int end = data.indexOf(")");
    return (start > 0 && end > start) ? data.substring(start, end) : "default";
}
}
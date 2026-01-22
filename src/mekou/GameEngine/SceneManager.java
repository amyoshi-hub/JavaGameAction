package mekou.GameEngine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private Map<String, String> sceneRegistry = new HashMap<>(); // 名前とパスの紐付け
    private mekou.GameEngine.Frame gameFrame;

    // 最初に stages.txt を読んで「どの名前がどのファイルか」を登録する
    public void registerStages(String listPath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(listPath));
            for (String line : lines) {
                String[] parts = line.split(":"); // stage1:Games/stages/stage1.txt
                sceneRegistry.put(parts[0].trim(), parts[1].trim());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static SceneManager getInstance(){
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    public void setFrame(mekou.GameEngine.Frame frame){
        this.gameFrame = frame;
    }

    public void load(String sceneName){
        loadAndSetScene(sceneName);
    }

    public void loadAndSetScene(String sceneName) {
        String path = sceneRegistry.get(sceneName);
        if (path != null) {
            gameFrame.initGame(path);
        }
    }
}

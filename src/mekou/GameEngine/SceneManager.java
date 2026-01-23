package mekou.GameEngine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import mekou.GameEngine.GameLib.GameMode;

public class SceneManager {
    private static SceneManager instance;
    
    private Stack<GameMode> modeHistory = new Stack<>();

    private Map<String, String> sceneRegistry = new HashMap<>(); // 名前とパスの紐付け
    private mekou.GameEngine.Frame gameFrame;

    public static void init(GameMode initialMode) {
        getInstance().modeHistory.clear();
        getInstance().modeHistory.push(initialMode);
        System.out.println("SceneManager initialized with: " + initialMode);
    }

    // 最初に stages.txt を読んで「どの名前がどのファイルか」を登録する
    public void registerStages(String listPath) {
        System.out.println("--- Stage Registration Start ---");
        System.out.println("Looking for file at: " + Paths.get(listPath).toAbsolutePath());
        try {
            List<String> lines = Files.readAllLines(Paths.get(listPath));
            for (String line : lines) {
                if(line.trim().isEmpty() || !line.contains(":")) continue;
                String[] parts = line.split(":");
                sceneRegistry.put(parts[0].trim(), parts[1].trim());
                System.out.println("Registered Stage: " + sceneRegistry);
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
        String path = sceneRegistry.get(sceneName);
        if(path != null){
            gameFrame.initGame(path);
        }
    }

    public GameMode getCurrentGameMode(){
        return modeHistory.peek();
    }
    public void pushMode(GameMode newMode){
        modeHistory.push(newMode);
    }
    public void popMode(){
        if(modeHistory.size() > 1){
            GameMode oldMode = modeHistory.pop();
            System.out.println("GameModeが移行します" + oldMode + "->" + getCurrentGameMode());
        }
    }
}

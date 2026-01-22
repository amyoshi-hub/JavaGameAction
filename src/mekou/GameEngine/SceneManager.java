package mekou.GameEngine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    public enum GameMode {
        TITLE,
        STAGE,
        GAMEOVER,
        CLEAR,
        DIALOG,
        SHOP,
        MENU
    }
    private GameMode lastMode = GameMode.STAGE;

    private GameMode currentMode = GameMode.TITLE; // デフォルトはタイトルモード

    private Map<String, String> sceneRegistry = new HashMap<>(); // 名前とパスの紐付け
    private mekou.GameEngine.Frame gameFrame;

    // 最初に stages.txt を読んで「どの名前がどのファイルか」を登録する
    public void registerStages(String listPath) {
        System.out.println("--- Stage Registration Start ---");
        System.out.println("Looking for file at: " + Paths.get(listPath).toAbsolutePath());
        try {
            List<String> lines = Files.readAllLines(Paths.get(listPath));
            for (String line : lines) {
                if(line.trim().isEmpty() || !line.contains(":")) continue;
                String[] parts = line.split(":"); // stage1:Games/stages/stage1.txt
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

    public void loadAndSetScene(String sceneName) {
        String path = sceneRegistry.get(sceneName);
        if (path != null) {
            gameFrame.initGame(path);
        }
    }

    public void enterDialogueMode() {
        this.lastMode = this.currentMode; // 今のモード（STAGEなど）を保存
        this.currentMode = GameMode.DIALOG;
    }

    public void exitDialogueMode() {
        this.currentMode = lastMode;
    }

    public GameMode getCurrentGameMode(){
        return currentMode;
    }
}

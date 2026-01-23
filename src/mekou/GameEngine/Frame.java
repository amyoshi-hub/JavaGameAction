package mekou.GameEngine;

import javax.swing.*;
import mekou.ActionGame.*;
import mekou.Entities.*;
import mekou.Entities.UtilFunction.*;
import mekou.GameEngine.GameLib.GameMode;


public class Frame extends JFrame {
    private Gra mp;
    private Engine engine;
    private Movement move; // Movementを保持

    Frame() {

        this.mp = new Gra(); // 描画パネル

        super.getContentPane().add(this.mp);
        super.setSize(700, 400);
        setLocation(100, 100);
        setVisible(true);

        SceneManager sm = SceneManager.getInstance();
        sm.setFrame(this);
        sm.registerStages("Games/stages/Stages.txt");
        initGame("Games/stages/stage1.txt");
        System.out.println(sm.getCurrentGameMode());
    }

    public void initGame(String mapPath){
        if (engine != null) engine.stop(); 

        SceneManager.init(GameMode.values()[0]);

        Scene newScene = new Scene();
        newScene.setPanel(this.mp);
        this.mp.setScene(newScene);

        this.engine = new Engine(newScene);
        System.out.println("Engine Start");
        CameraShack.init(this.mp);
        
        MapLoader loader = new MapLoader(newScene);
        System.out.println("MapLoaded");
        
        loader.loadMap(mapPath);
        System.out.println("map applied");

        Player player = loader.getPlayer();
        if(player != null){
            this.mp.setCameraTarget(player);
            this.move = new Movement(newScene, player);
            this.engine.setMovement(this.move);
        }
        
        // 背景などの共通オブジェクト
        newScene.createObject(new backGround(0, 0, 1980, 1080));
    }

    public static void main(String[] args) {
        new Frame();
    }
}
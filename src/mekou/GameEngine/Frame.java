package mekou.GameEngine;

import java.awt.GridBagLayout;
import javax.swing.*;
import mekou.ActionGame.*;
import mekou.Entities.*;
import mekou.Entities.UtilFunction.*;
import mekou.GameEngine.Editor.EditorFrame;
import mekou.GameEngine.GameLib.GameMode;


public class Frame extends JFrame {
    private Gra mp;
    private Engine engine;
    private Movement move; // Movementを保持
    private JPanel titlePanel;


    Frame() {

        this.mp = new Gra(); // 描画パネル

        super.getContentPane().add(this.mp);
        super.setSize(700, 400);
        setLocation(100, 100);
        setVisible(true);

        SceneManager.getInstance().setFrame(this);

        showTitleMenu();
    }


    private void showTitleMenu() {
        titlePanel = new JPanel(new GridBagLayout());
        
        // mpを隠してtitlePanelを前面に出す
        this.mp.setVisible(false);
        this.getContentPane().add(titlePanel); 

        JButton playButton = new JButton("冒険を始める");
        JButton editorButton = new JButton("エディタを起動");

        // --- プレイボタン ---
        playButton.addActionListener(e -> {
            playGame(); // ここでtitlePanelをremoveしてmpを出す
        });

        // --- エディタボタン ---
        editorButton.addActionListener(e -> {
            // エディタ起動時はタイトルを消してゲーム画面を出し、その横に別窓を出す
            this.remove(titlePanel);
            this.mp.setVisible(true);
            startEditor(); 
            this.revalidate();
            this.repaint();
        });

        titlePanel.add(playButton);
        titlePanel.add(editorButton);
        
        this.revalidate();
        this.repaint();
    }

    public void playGame(){
        this.remove(titlePanel);
        SceneManager.getInstance().registerStages("Games/stages/Stages.txt");
        initGame("Games/stages/stage1.txt");
    }

    private void startEditor(){
        initGame("Games/stages/stage1.txt");
        Scene currentScene = this.mp.getScene(); //見えているシーンからぶんどる
        EditorFrame.getInstance(currentScene);
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
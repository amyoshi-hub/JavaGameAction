package mekou.GameEngine;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import mekou.ActionGame.*;
import mekou.Entities.*;
import mekou.Entities.UtilFunction.*;
import mekou.GameEngine.Editor.EditorFrame;
import mekou.GameEngine.GameLib.GameMode;


public class Frame extends JFrame {
    private final Gra mp;
    private Engine engine;
    private Movement move; // Movementを保持
    private JPanel titlePanel;
    private static Frame instance;
    private int baseScreenSizeX = 1300;
    private int baseScreenSizeY = 600;


    Frame() {
        instance = this;
        this.mp = new Gra(); // 描画パネル

        this.setLayout(new BorderLayout());
        this.add(this.mp, BorderLayout.CENTER);

        super.setSize(baseScreenSizeX, baseScreenSizeY);

        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        SceneManager.getInstance().setFrame(this);

        showTitleMenu();
    }


    private void showTitleMenu() {
        titlePanel = new JPanel(new GridBagLayout());
        
        // mpを隠してtitlePanelを前面に出す
        this.mp.setVisible(false);
        this.getContentPane().add(titlePanel); 
        
        this.add(titlePanel, BorderLayout.CENTER);

        JButton playButton = new JButton("冒険を始める");
        JButton editorButton = new JButton("エディタを起動");

        // --- プレイボタン ---
        playButton.addActionListener(e -> {
            showGameSelection(false); // ここでtitlePanelをremoveしてmpを出す
        });

        // --- エディタボタン ---
        editorButton.addActionListener(e -> {
            // エディタ起動時はタイトルを消してゲーム画面を出し、その横に別窓を出す
            showGameSelection(true);
        });

        titlePanel.add(playButton);
        titlePanel.add(editorButton);
        
        this.revalidate();
        this.repaint();
    }

    private void showGameSelection(boolean EditorMode) {
        titlePanel.removeAll(); // 「遊ぶ」「エディタ」ボタンを消す
        
        File gamesDir = new File("Games");
        File[] games = gamesDir.listFiles(File::isDirectory); // フォルダだけ取得

        if (games != null) {
            for (File game : games) {
                JButton gameButton = new JButton(game.getName()); // フォルダ名をボタンに
                gameButton.addActionListener(e -> {
                    // そのゲームのフォルダにあるステージを読み込んで開始！
                    String gameFockPath = game.getPath() + "/stage1.txt";
                    launchGame(gameFockPath);
                    if (EditorMode){
                        this.remove(titlePanel);
                        this.mp.setVisible(true);
                        initGame(gameFockPath);
                        Scene currentScene = this.mp.getScene();
                        EditorFrame.getInstance(currentScene);
                    }else{
                        initGame(gameFockPath);
                    }
                });
                titlePanel.add(gameButton);
            }
        }
        titlePanel.revalidate();
        titlePanel.repaint();
    }

    public void launchGame(String startMapPath) {
        this.remove(titlePanel);
        this.mp.setVisible(true); // パネルを再表示
        this.add(this.mp, BorderLayout.CENTER);
        // 必要ならここで SceneManager.getInstance().registerStages(...) も呼ぶ
        initGame(startMapPath);
        this.revalidate();
        this.repaint();
    }

    public InputStream getStageStream(String path) {
        try {
            File externalFile = new File(path);
            if (externalFile.exists()) {
                // 1. まずは外側のフォルダを探す（書き換え用）
                return new FileInputStream(externalFile);
            } else {
                // 2. なければjarの内部を探す（学校用・パッキング済み）
                // パスが / で始まるように調整
                String resourcePath = path.startsWith("/") ? path : "/" + path;
                InputStream is = getClass().getResourceAsStream(resourcePath);
                if (is == null) throw new IOException("Resource not found: " + path);
                return is;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        newScene.createObject(new backGround(0, 0, baseScreenSizeX + 1000, baseScreenSizeY));
    }

    public static Frame getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        new Frame();
    }
}
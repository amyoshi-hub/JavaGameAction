package mekou;

import java.awt.*;
import javax.swing.*;
import mekou.UtilObjects.*;


class Frame extends JFrame {
    private Gra mp;
    private Movement move; // Movementを保持
    private Image[] images;
    private Toolkit tk;

    Frame() {

        this.mp = new Gra(); // 描画パネル
        Scene scene = new Scene();
        scene.setPanel(this.mp); // Sceneにパネルを教えてあげる

        this.mp.setScene(scene);

        // エンジン生成
        Engine engine = new Engine(scene);
        
        MapLoader loader = new MapLoader(scene);
        loader.loadMap("../stages/stage1.txt");
        // Movementに操作対象(player)を渡す

        Player player = loader.getPlayer();
        if(player != null){
            this.mp.setCameraTarget(player);
            this.move = new Movement(scene, player);
            engine.setMovement(this.move);
        }
        
        backGround bg = new backGround(0, 0, 1980, 1080);
        scene.createObject(bg);
        

        super.getContentPane().add(this.mp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocation(100, 100);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }
}
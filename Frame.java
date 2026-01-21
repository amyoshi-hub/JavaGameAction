import java.awt.*;
import java.net.*;
import javax.swing.*;

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
        // プレイヤー生成とSceneへの登録
        Player player = new Player();
        scene.createObject(player); 

        // Movementに操作対象(player)を渡す
        this.move = new Movement(scene, player);

        super.getContentPane().add(this.mp);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocation(100, 100);
        setVisible(true);
    }

    private void GetImage() {
    this.images = new Image[3]; 
    for(int i = 0; i < 3; i++) {
        String path = "img/test" + i + ".png"; // JAR内でのパス
        
        // JAR内からリソースとしてURLを取得
        URL url = this.getClass().getClassLoader().getResource(path);
        
        if (url != null) {
            // URLを使ってImageIconを作る（これでJAR化しても大丈夫）
            ImageIcon icon = new ImageIcon(url);
            images[i] = icon.getImage();
            System.out.println("読み込み成功: " + path);
        } else {
            System.err.println("リソースが見つかりません: " + path);
        }
    }
}

    public static void main(String[] args) {
        new Frame();
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.net.*;

class Frame extends JFrame {
    private Gra mp;
    private Movement move; // Movementを保持
    private Image[] images;
    private Toolkit tk;

    Frame() {
        this.tk = Toolkit.getDefaultToolkit();
        GetImage(); // 画像の読み込み

        this.mp = new Gra();
        // 画像をmpにセット（アニメーション用）
        this.mp.setAnimation(64, 64, images);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(this.mp, BorderLayout.CENTER);

        // ここでMovementを呼び出す！
        // panel1とmpを渡すことで、Movement内でキー入力と描画更新ができるようになる
        this.move = new Movement(panel1, this.mp);

        super.getContentPane().add(panel1);

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
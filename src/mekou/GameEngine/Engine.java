package mekou.GameEngine;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mekou.ActionGame.Movement;

public class Engine implements ActionListener {
    private Movement movement;
    private Scene scene;
    private Timer timer;

    public Engine(Scene scene) {

        this.scene = scene;
        this.timer = new Timer(31, this); // 約24FPS
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() != this.timer) return;
        //UIMode
        if(SceneManager.getInstance().getCurrentGameMode() == SceneManager.GameMode.DIALOG){
            //((Gra)scene.getPanel()).getUIManager().update();
            scene.getPanel().repaint();
            Toolkit.getDefaultToolkit().sync();
            return;
        }

        //通常処理
        if (movement != null) {
            movement.applyInput();
        }            scene.updateAll();
        scene.updateAll();
        scene.CollisionCheck();
        scene.getPanel().repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    public void stop() {
        this.timer.stop();
    }

    public void updateScene(Scene scene) {
        this.scene = scene;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }
}

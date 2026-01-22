package mekou;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
        if (e.getSource() == this.timer) {
            if (movement != null) {
                movement.applyInput();
            }
            scene.updateAll();
            scene.CollisionCheck();
            scene.getPanel().repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }


    public void setMovement(Movement movement) {
        this.movement = movement;
    }
}

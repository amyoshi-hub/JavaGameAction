package mekou.GameEngine;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mekou.ActionGame.Movement;
import mekou.GameEngine.Editor.EditorFrame;
import mekou.GameEngine.GameLib.*;

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
        if(EditorFrame.getInstance() != null){
            EditorFrame.getInstance().update();
        }
        
        if(e.getSource() != this.timer) return;
        //UIMode
        if(SceneManager.getInstance().getCurrentGameMode() == GameMode.DIALOG){
            if(movement != null) movement.applyInput();
            scene.animationUpdateAll();
            render();
            return;
        }

        if(movement != null) movement.applyInput();
        scene.updateAll();
        scene.CollisionCheck();
        render();
    }

    private void EditorCall(){
        if(EditorFrame.getInstance() != null){
            EditorFrame.getInstance().update();
        }
    }

    private void render(){
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

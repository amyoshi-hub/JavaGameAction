import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Movement implements ActionListener{

	private Scene scene;
	private Timer time;
	private boolean leftPressed, rightPressed, spacePressed;
	private Player player;
	private JPanel panel;


	public Movement(Scene scene, Player player){
		this.scene = scene;
		this.player = player;
		this.panel = scene.getPanel();
		setKeyBindings(panel);

		//24FPSを基準に考える
		this.time = new Timer(34, this);
		this.time.start();
	}

	private void setKeyBindings(JPanel panel){
		InputMap im = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);	
		ActionMap am = panel.getActionMap();
	
		im.put(KeyStroke.getKeyStroke("A"), "leftAction");
    		im.put(KeyStroke.getKeyStroke("released A"), "leftReleased");
    		am.put("leftAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { leftPressed = true; } });
    		am.put("leftReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { leftPressed = false; } });

    		im.put(KeyStroke.getKeyStroke("D"), "rightAction");
    		im.put(KeyStroke.getKeyStroke("released D"), "rightReleased");
    		am.put("rightAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { rightPressed = true; } });
    		am.put("rightReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { rightPressed = false; } });

    		im.put(KeyStroke.getKeyStroke("SPACE"), "spaceAction");
    		im.put(KeyStroke.getKeyStroke("released SPACE"), "spaceReleased");
    		am.put("spaceAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { spacePressed = true; } });
    		am.put("spaceReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { spacePressed = false; } });
	}

	public void actionPerformed(ActionEvent e){
		int  vx = 0;
		if(e.getSource() == this.time){
			if(leftPressed) vx -= 5;	
			if(rightPressed) vx += 5;	
		}	
		player.setVX(vx);
		if(spacePressed){
			player.jump();
		}

		scene.updateAll();

		scene.getPanel().repaint(); 
        Toolkit.getDefaultToolkit().sync();
	}
}

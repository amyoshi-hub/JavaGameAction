package mekou;

import java.awt.event.*;
import javax.swing.*;
import mekou.interfaces.Controllable;

public class Movement{

	private Controllable target;
	private boolean leftPressed, rightPressed, spacePressed;
	
	private JPanel panel;


	public Movement(Scene scene, Controllable target){
		this.target = target;
		this.panel = scene.getPanel();
		setKeyBindings(panel);
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

	public void applyInput(){
		int  vx = 0;
		
		if(leftPressed) vx -= 5;	
		if(rightPressed) vx += 5;
		
		target.setVX(vx);

		if(spacePressed){
			target.jump();
		}
	}
}

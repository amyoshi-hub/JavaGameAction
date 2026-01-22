package mekou.ActionGame;

import java.awt.event.*;
import javax.swing.*;
import mekou.GameEngine.Scene;
import mekou.GameEngine.SceneManager;
import mekou.GameEngine.SceneManager.GameMode;
import mekou.GameEngine.interfaces.Controllable;

public class Movement{

	private Controllable target;
	private boolean leftPressed, rightPressed, spacePressed, attackPressed, CrouchPressed, UpperAction;
	
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

			im.put(KeyStroke.getKeyStroke("C"), "AttackAction");
			im.put(KeyStroke.getKeyStroke("released C"), "AttackReleased");
			am.put("AttackAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { attackPressed = true; } });
			am.put("AttackReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { attackPressed = false; } });

			im.put(KeyStroke.getKeyStroke("S"), "CrouchAction");
			im.put(KeyStroke.getKeyStroke("released S"), "CrouchReleased");
			am.put("CrouchAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { CrouchPressed = true; } });
			am.put("CrouchReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { CrouchPressed = false; } });

			im.put(KeyStroke.getKeyStroke("W"), "UpperAction");
			im.put(KeyStroke.getKeyStroke("released W"), "UpperReleased");
			am.put("UpperAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { UpperAction = true; } });
			am.put("UpperReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { UpperAction = false; } });
		}

	public void applyInput(){
		GameMode currentMode = SceneManager.getInstance().getCurrentGameMode();
		if(currentMode == GameMode.DIALOG){
			if(spacePressed) {
				target.getScene().getDialogueManager().onNext();
				spacePressed = false;
			}
			target.setVX(0);
			return;
		}
		int  vx = 0;
		
		if(leftPressed) vx -= 5;	
		if(rightPressed) vx += 5;
		
		target.setVX(vx);

		if(spacePressed){
			target.jump();
			//Eventで処理
		}
		if(CrouchPressed && attackPressed==true){
			target.downAttack();
		}
		if(UpperAction && attackPressed==true){
			target.upperAction();
		}
		if(attackPressed){
			target.attack();
		}
	}
}

package mekou.ActionGame;

import java.awt.event.*;
import javax.swing.*;
import mekou.GameEngine.GameLib.GameMode;
import mekou.GameEngine.Scene;
import mekou.GameEngine.SceneManager;
import mekou.GameEngine.UI.DialogueManager;
import mekou.GameEngine.interfaces.Controllable;

public class Movement{

	private Controllable target;
	private boolean leftPressed, rightPressed, spacePressed, attackPressed, CrouchPressed, UpperAction, interactPressed;
	private boolean interactWasPressed = false;
	private JPanel panel;
	private int specialAttackCound = 0;


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

			im.put(KeyStroke.getKeyStroke("E"), "interactAction");
			im.put(KeyStroke.getKeyStroke("released E"), "interactReleased");

			am.put("interactAction", new AbstractAction() { public void actionPerformed(ActionEvent e) { interactPressed = true; } });
			am.put("interactReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { interactPressed = false; } });
		}

	public void applyInput(){
		GameMode currentMode = SceneManager.getInstance().getCurrentGameMode();
		boolean isAnyInteracting = spacePressed || interactPressed;
		if(currentMode == GameMode.DIALOG){
			if(spacePressed || interactPressed && !interactWasPressed) {
					target.getScene().getDialogueManager().onNext();
					spacePressed = false;
				}
				isAnyInteracting = (spacePressed || interactPressed);
			target.setVX(0);
			return;
		}

		if(interactPressed){
			System.out.println("interactPressed pressed" + target.getCanAction());
			if(target.getCanAction()){
				// 待機していた ID で会話を開始
				String id = target.getPendingDialogId();
				System.out.println("会話を試みる" + id);
				DialogueManager.getInstance().startDialogue(id);
					
				interactPressed = false; // 会話開始に消費。ジャンプさせない！
			}
		}

		int  vx = 0;
		
		if(leftPressed) vx -= 5;	
		if(rightPressed) vx += 5;
		
		target.setVX(vx);

		if(spacePressed){
				target.jump();
				spacePressed = false;
    	}

		
		if(CrouchPressed && attackPressed==true && specialAttackCound < 10){
			target.downAttack();
			specialAttackCound++;
		}
		if(UpperAction && attackPressed==true && specialAttackCound < 5){
			target.upperAction();
			specialAttackCound++;
		}
		if(attackPressed){
			target.attack();
		}
		if(!attackPressed){
			specialAttackCound = 0;
		}
	}
}

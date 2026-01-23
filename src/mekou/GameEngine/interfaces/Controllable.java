package mekou.GameEngine.interfaces;

import mekou.GameEngine.Scene;

public interface Controllable {
    public void setVX(float vx);
    public void setVY(float vy);
    public void jump();

    void attack();
    void upperAction();
    void downAttack();
    Scene getScene();
    boolean getCanAction();
    String getPendingDialogId();
}

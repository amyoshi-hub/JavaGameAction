package mekou.GameEngine.interfaces;

public interface Controllable {
    public void setVX(float vx);
    public void setVY(float vy);
    public void jump();

    void attack();
    void upperAction();
    void downAttack();
}

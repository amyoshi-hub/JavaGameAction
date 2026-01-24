package mekou.Entities;
import mekou.GameEngine.GameObject;

public class Decal extends GameObject {
    public Decal(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.z = -1;
        this.anim.setState("idle");
        this.useGravity = false;
    }

    @Override
    public void update() {
        this.tick++;
    }

    public void setAnimState(String animState){
        this.anim.setState(animState);
    }
}
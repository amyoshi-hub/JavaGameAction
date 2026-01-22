package mekou.Entities;
import mekou.GameEngine.GameObject;

public class Decal extends GameObject {
    public Decal(int x, int y, int z, String animState) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.anim.setState(animState);
        this.useGravity = false;
    }

    @Override
    public void update() {
        this.tick++;
    }
}
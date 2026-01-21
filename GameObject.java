public abstract class GameObject {
    protected float x, y;
    protected int z;
    protected int tick = 0;
    protected Animation anim;

    public GameObject() {
        this.anim = new Animation();
    }

    // 毎フレームの計算
    public void update() {
        tick++;
    }

    // 描画処理（Sceneから呼ばれる）
    public void draw(Graphics g) {
        if (anim != null) {
            Image frame = anim.getCurrentFrame(tick);
            if (frame != null) {
                g.drawImage(frame, (int)x, (int)y, null);
            }
        }
    }

    public int getZ() { return z; }
}
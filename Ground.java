public class Ground extends GameObject {

    public Ground(float x, float y, int w, int h) {
        super();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.z = -1;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, width, height);
    }
}
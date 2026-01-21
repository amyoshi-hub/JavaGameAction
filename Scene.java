import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Scene{
    private List<GameObject> objects = new ArrayList<>();
    private JPanel panel;
    

    public void setPanel(JPanel panel){
        this.panel = panel;
    }
    public JPanel getPanel(){
        return this.panel;
    }
    
    //z-buffer
    public <T extends GameObject> T createObject(T obj){
        this.objects.add(obj);
        return obj;
    }

    //描画更新
    public void updateAll(){
        objects.sort((a, b) -> Integer.compare(a.getZ(), b.getZ()));
        for(GameObject obj : objects){
            obj.update();
        }
    }

    public void drawAll(Graphics g){
        for(GameObject obj : objects){
            obj.draw(g);
            }
    }

    public void CollisionCheck(){
        for (GameObject a : objects) {
            for (GameObject b : objects) {
                if (a != b && a.getBounds().intersects(b.getBounds())) {
                    // 衝突時のイベント発生！
                    //player or enemy or objectのhealth->takeDamage()などを呼び出す
                }
            }
        }
    }
}
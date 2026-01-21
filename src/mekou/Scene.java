package mekou;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import mekou.UtilObjects.*;
import mekou.interfaces.*;

public class Scene{
    private List<GameObject> objectsToAdd = new ArrayList<>();
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
        obj.setScene(this);
        this.objectsToAdd.add(obj);
        return obj;
    }

    //描画更新
    public void updateAll(){
        if(!objectsToAdd.isEmpty()){
            objects.addAll(objectsToAdd);
            objectsToAdd.clear();
        }
        objects.sort((a, b) -> Integer.compare(a.getZ(), b.getZ()));
        for(GameObject obj : objects){
            obj.update();
        }
        objects.removeIf(obj -> !obj.isActive());
    }

    public void drawAll(Graphics g){
        for(GameObject obj : objects){
            obj.draw(g);
            }
    }

    public void CollisionCheck(){
        for (GameObject a : objects) {
            for (GameObject b : objects) {
                if (a == b) continue; // 自分自身とは衝突しない
                if (a != b && a.getBounds().intersects(b.getBounds())) {
                    if (b instanceof Damageable && a instanceof AttackBoxInterface) {
                            Damageable target = (Damageable) b;
                            AttackBoxInterface attackBox = (AttackBoxInterface) a;
                            target.takeDamage(attackBox.getDamage());
                            createObject(new SparkEffect(b.getX(), b.getY()));
                    }
                    if(b instanceof Damageable && a instanceof Attack){
                        Damageable target = (Damageable) b;
                        Attack attacker = (Attack) a;
                        attacker.attack();
                        target.takeDamage(10); // 固定ダメージ
                        createObject(new SparkEffect(b.getX(), b.getY()));
                    }

                    if (b instanceof Ground && a instanceof Collider) {
                        // プレイヤーが地面に衝突した場合の処理
                        System.out.println("Hit Ground!");
                        a.land(b.getY());
                    }
                    if (a instanceof AttackBox) {
                        ((AttackBox) a).setActive(false);
                    }
                }
            }
        }
    }
}
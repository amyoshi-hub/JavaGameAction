package mekou.GameEngine;

import java.util.List;

import mekou.Entities.*;
import mekou.GameEngine.interfaces.*;
import mekou.ActionGame.*;

public class CollisionManager {
    private Scene scene;

    public CollisionManager(Scene scene) {
        this.scene = scene;
    }

    public void checkAll(List<GameObject> objects, GameObject player) {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject a = objects.get(i);
                GameObject b = objects.get(j);

                if (a.getBounds().intersects(b.getBounds())) {
                    resolve(a, b, player);
                }
            }
        }
    }

    private void resolve(GameObject a, GameObject b, GameObject player) {
        checkHitInteractions(a, b);
        checkPhisicsInteractions(a, b);
        checkSceneTransition(a, b, player);
        if (a instanceof AttackBox){
            ((AttackBox) a).setActive(false);
        }
    }

    private void checkHitInteractions(GameObject a, GameObject b) {
        if (a instanceof AttackBoxInterface && b instanceof Damageable) {
            Damageable target = (Damageable) b;
            AttackBoxInterface attackBox = (AttackBoxInterface) a;
            target.takeDamage(attackBox.getDamage());
            scene.createObject(new SparkEffect(b.getX(), b.getY()));
        }
        if (a instanceof Damageable && b instanceof Attack) {
            Damageable target = (Damageable) a;
            Attack attacker = (Attack) b;
            attacker.attack();
            target.takeDamage(10); // 固定ダメージ
            scene.createObject(new SparkEffect(a.getX(), a.getY()));
        }
    }

    private void checkPhisicsInteractions(GameObject a, GameObject b) {
        if (a instanceof Collider && b instanceof Ground) {
        a.land(b.getY());
        ((Collider) a).onCollide(b);
        } 
        // パターン2: bがCollider、aがGroundの場合
        else if (b instanceof Collider && a instanceof Ground) {
            b.land(a.getY());
            ((Collider) b).onCollide(a);
        }
    }

    private void checkSceneTransition(GameObject a, GameObject b, GameObject player) {
        // aがトリガー、bがプレイヤーのパターン
        if (a instanceof SceneTriger && b instanceof Player) {
            ((SceneTriger) a).onCollide(b);
        } 
        // bがトリガー、aがプレイヤーのパターン
        else if (b instanceof SceneTriger && a instanceof Player) {
            ((SceneTriger) b).onCollide(a);
        }
    }

}
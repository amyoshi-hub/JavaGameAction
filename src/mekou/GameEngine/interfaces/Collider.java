package mekou.GameEngine.interfaces;
import mekou.GameEngine.GameObject;

public interface Collider {
    // 衝突判定用インターフェース
    //ここにland関数を置くべきでは？land->OnClide
    void land(float groundY);
    void onCollide(GameObject other);
}

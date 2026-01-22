package mekou.GameEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import mekou.GameEngine.UI.DialogueManager;

public class Scene{
    private List<GameObject> objectsToAdd = new ArrayList<>();
    private List<GameObject> objects = new ArrayList<>();
    private JPanel panel;
    private GameObject player;
    private CollisionManager collisionManager;

    public Scene(){
        this.collisionManager = new CollisionManager(this);
    }

    public GameObject getPlayer(){
        return this.player;
    }

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

    public void setSceneTriger(int x, int y, String target){
        SceneTriger triger = new SceneTriger(x, y, target);
        createObject(triger);
    }

    //描画更新
    public void updateAll(){
        if(!objectsToAdd.isEmpty()){
            objects.addAll(objectsToAdd);
            objectsToAdd.clear();
        }
        objects.removeIf(obj -> obj == null);
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
        collisionManager.checkAll(objects, player);
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }

    public DialogueManager getDialogueManager() {
        return DialogueManager.getInstance(); 
    }
}
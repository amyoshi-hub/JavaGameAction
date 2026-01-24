package mekou.GameEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import mekou.ActionGame.Player;
import mekou.GameEngine.UI.DialogueManager;

public class Scene{
    private List<GameObject> objectsToAdd = new ArrayList<>();
    private List<GameObject> objects = new ArrayList<>();
    private JPanel panel;
    private CollisionManager collisionManager;
    private boolean needRefresh = false;

    public Scene(){
        this.collisionManager = new CollisionManager(this);
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

    private void prepareUpdate(){
        int beforeSize = objects.size();

        if(!objectsToAdd.isEmpty()){
            objects.addAll(objectsToAdd);
            objectsToAdd.clear();
        }

        mekou.GameEngine.UI.UIProcess.getInstance().update();

        objects.removeIf(obj -> obj == null || !obj.isActive());

        if(objects.size() != beforeSize){
            this.needRefresh = true;
        }

        objects.sort((a, b) -> Integer.compare(a.getZ(), b.getZ()));
    }

    //描画更新
    public void updateAll(){
        prepareUpdate();
        for(GameObject obj : objects){
            obj.update();
            obj.updateAnimaion();
        }
        objects.removeIf(obj -> !obj.isActive());
    }

    public void animationUpdateAll(){
        prepareUpdate();
        for(GameObject obj : objects){
            obj.updateAnimaion();
        }
    }

    public void drawAll(Graphics g){
        for(GameObject obj : objects){
            obj.draw(g);
            }
    }

    public void CollisionCheck(){
        collisionManager.checkAll(objects, Player.getInstance());
    }

    public DialogueManager getDialogueManager() {
        return DialogueManager.getInstance(); 
    }
    public List<GameObject> getObjects() {
        return this.objects;
    }
    public boolean needRefresh(){
        return this.needRefresh;
    }
    public void setNeedRefresh(boolean b){
        this.needRefresh = b;
    }
}
package mekou.GameEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import mekou.ActionGame.Player;
import mekou.Entities.SceneTriger;
import mekou.GameEngine.UI.DialogueManager;

public class Scene{
    private List<GameObject> objectsToAdd = new ArrayList<>();
    private List<GameObject> objects = new ArrayList<>();
    private JPanel panel;
    private CollisionManager collisionManager;
    private boolean needRefresh = false;
    private boolean isRunning = false;

    public Scene(){
        this.collisionManager = new CollisionManager(this);
    }

    public void setPanel(JPanel panel){
        this.panel = panel;
    }
    
    //z-buffer
    public <T extends GameObject> T createObject(T obj){
        obj.setScene(this);
        this.objectsToAdd.add(obj);
        return obj;
    }

    public void setSceneTriger(int x, int y, String target){
        SceneTriger triger = new SceneTriger(x, y);
        triger.setTargetSceneName(target);
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
            if(isRunning){
                obj.update();
            }
            obj.updateAnimation();
        }
        objects.removeIf(obj -> !obj.isActive());
    }

    public void animationUpdateAll(){
        prepareUpdate();
        for(GameObject obj : objects){
            obj.updateAnimation();
        }
    }

    public void initSpawnPoint(){
        for(GameObject obj : objects){
            obj.recordSpawnPoint();
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
    public JPanel getPanel(){
        return this.panel;
    }

    //動かすかどうか
    public void setRunning(boolean b) { this.isRunning = b; }
    public boolean isRunning() { return this.isRunning; }
}
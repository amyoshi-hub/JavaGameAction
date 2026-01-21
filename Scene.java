public class Scene{
    private List<GameObject> objects = new ArrayList<>();

//z-buffer
    public <T extends GameObject> T createObject(T obj){
        this.objects.add(obj);
        return obj;
    }

//描画更新
    public void updateAll(){
        objects.sort((a, b) -> Integer.compare(a.getZ(), b.getZ()))
        for(GameObject obj : objects){
            obj.update();
        }
    }
    public void drawAll(Graphic g){
        for(GameObject obj : objects){
            obj.draw(g);
            }
    }
}
package mekou.GameEngine.UI;

public class DialoguePage {
    private String name;
    private String text;

    public DialoguePage(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
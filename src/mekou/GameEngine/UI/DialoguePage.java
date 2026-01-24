package mekou.GameEngine.UI;

public class DialoguePage {
    private final String name;
    private final String text;

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
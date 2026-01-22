package mekou.UtilObjects;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DialogueManager {
    public void drawDialogue(Graphics g, String name, String text) {
        g.setColor(new Color(0, 0, 0, 200)); // 半透明の黒
        g.fillRect(50, 250, 600, 100);       // メッセージ枠
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("MS Gothic", Font.BOLD, 16));
        g.drawString("【" + name + "】", 70, 275);
        g.drawString(text, 70, 310);
    }
}

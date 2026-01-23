package mekou.GameEngine.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mekou.GameEngine.SceneManager;

public class DialogueManager {
    private static DialogueManager instance;
    private String fullText = "";
    private int visibleCount = 0;
    private int speed = 10; // なんフレームで1文字表示するか
    private int frameCounter = 0;
    private int currentPageIndex = 0;
    private Map<String, List<DialoguePage>> dialogCache = new HashMap<>();
    private List<DialoguePage> pages = new ArrayList<>();

    public void update(){
        frameCounter++;
        if(frameCounter >= speed && visibleCount < fullText.length()){
            visibleCount++;
        }
    }

    public void onNext(){
        if(visibleCount < fullText.length()){
            visibleCount = fullText.length();
        }else{
            nextPage();
        }
    }

    public void nextPage(){
        currentPageIndex++;
        if(currentPageIndex < pages.size()){
            DialoguePage page = pages.get(currentPageIndex);
            startDialogue(page.getText());
        }else{
            SceneManager.getInstance().exitDialogueMode();
        }
    }


    public void startDialogue(String dialogID) {
        SceneManager.getInstance().enterDialogueMode();

        // 1. もしキャッシュになかったら「読み込み処理」をする
        if(!dialogCache.containsKey(dialogID)){
            loadDialogueFile(dialogID);            
            dialogCache.put(dialogID, pages);
        }

        // 2. キャッシュからリストを取り出す
        this.pages = dialogCache.get(dialogID);
        this.currentPageIndex = 0;

        // 3. 最初のページを表示
        if(pages != null && !pages.isEmpty()){
            displayPage(pages.get(0));
        }
    }

    private void loadDialogueFile(String dialogID) {
        List<DialoguePage> newPages = new ArrayList<>();
        // パスを構築: stages/DIALOG/ + dialog_01 + .txt
        String path = "Games/stages/DIALOG/" + dialogID + ".txt";

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(path))) {
            String line;
            String currentName = "???";
            StringBuilder currentText = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("speacker:")) {
                    currentName = line.substring("speacker:".length()).trim();
                } else if (line.equals("-")) {
                    // 区切り線が来たら、それまでの内容をページとして保存
                    newPages.add(new DialoguePage(currentName, currentText.toString().trim()));
                    currentText.setLength(0); // テキストをリセット
                } else {
                    // それ以外は本文（複数行対応）
                    currentText.append(line).append("\n");
                }
            }
            // 最後のページを追加（ファイル末尾に - がない場合用）
            if (currentText.length() > 0) {
                newPages.add(new DialoguePage(currentName, currentText.toString().trim()));
            }
        } catch (java.io.IOException e) {
            System.err.println("Dialogue file not found: " + path);
            newPages.add(new DialoguePage("System", "Error: File not found."));
        }
        dialogCache.put(dialogID, newPages);
    }

    public void drawDialogue(Graphics g, String name, String text) {
        g.setColor(new Color(0, 0, 0, 200)); // 半透明の黒
        g.fillRect(50, 250, 600, 100);       // メッセージ枠
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("MS Gothic", Font.BOLD, 16));
        g.drawString("【" + name + "】", 70, 275);
        g.drawString(text, 70, 310);
    }

    public void draw(Graphics2D g2) {
        if (pages == null || pages.isEmpty()) return;

        DialoguePage currPage = pages.get(currentPageIndex);
        String subText = fullText.substring(0, visibleCount);
    
        // ここで座布団と文字を描画
        drawDialogue(g2, currPage.getName(), subText);
    }

    private void displayPage(DialoguePage page) {
        this.fullText = page.getText();
        this.visibleCount = 0;
        this.frameCounter = 0;
    }

    public static DialogueManager getInstance() {
        if (instance == null) {
            instance = new DialogueManager();
        }
        return instance;
    }
}

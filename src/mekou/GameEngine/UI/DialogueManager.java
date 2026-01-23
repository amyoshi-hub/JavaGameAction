package mekou.GameEngine.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mekou.GameEngine.GameLib.GameMode;
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
            //visibleCount++;
            visibleCount = Math.min(frameCounter / speed, fullText.length());
        }
        System.out.println(visibleCount);
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
            displayPage(page);
        }else{
            exitDialogueMode();
        }
    }


    public void startDialogue(String dialogID) {
        enterDialogueMode();

        // 1. キャッシュになければ読み込む (load内部でキャッシュへのputまで完結させる)
        if(!dialogCache.containsKey(dialogID)){
            loadDialogueFile(dialogID); 
        }

        // 2. キャッシュから取り出す
        this.pages = dialogCache.get(dialogID);
        this.currentPageIndex = 0;

        // 3. データの生存確認ログ（これを出せば安心！）
        if(pages != null && !pages.isEmpty()){
            System.out.println("DEBUG: Pages found! Size: " + pages.size());
            displayPage(pages.get(0));
        } else {
            System.out.println("DEBUG: Pages is still empty for ID: " + dialogID);
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
            System.out.println("Dialog 中身");
            for (DialoguePage p : newPages) {
                System.out.println("Name: [" + p.getName() + "] Text: [" + p.getText().replace("\n", " ") + "]");
            }
    }

    public void draw(Graphics2D g2) {
        if (pages == null || pages.isEmpty() || currentPageIndex >= pages.size()) return;

        //System.out.println("dialog draw is working");
        DialoguePage currPage = pages.get(currentPageIndex);
        int end = Math.min(visibleCount, fullText.length());
        String subText = fullText.substring(0, end);

        // ここで座布団と文字を描画
        drawDialogue(g2, currPage.getName(), subText);
    }

    public void drawDialogue(Graphics g, String name, String text) {
        g.setColor(new Color(0, 0, 0, 200)); // 半透明の黒
        g.fillRect(50, 50, 600, 150);       // メッセージ枠
        
        //System.out.println(name);
        //System.out.println(text);

        g.setColor(Color.WHITE);
        g.setFont(new Font("MS Gothic", Font.BOLD, 18));
        g.drawString("【" + name + "】", 70, 80);
        g.setFont(new Font("MS Gothic", Font.PLAIN, 16));
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], 80, 110 + (i * 25));
        }
    }

    private void displayPage(DialoguePage page) {
        this.fullText = page.getText();
        this.visibleCount = 0;
        this.frameCounter = 0;
        //System.out.println("DEBUG: displayPage set fullText to: " + this.fullText);
    }

    public static DialogueManager getInstance() {
        if (instance == null) {
            instance = new DialogueManager();
        }
        return instance;
    }

    public void enterDialogueMode() {
        SceneManager.getInstance().pushMode(GameMode.DIALOG);
    }

    private void exitDialogueMode() {
        currentPageIndex = 0;
        pages = new ArrayList<>();
        fullText = "";
        SceneManager.getInstance().popMode();
    }
}

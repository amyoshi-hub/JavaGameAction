package mekou.GameEngine.Editor;

import javax.swing.*;
import java.awt.BorderLayout;
import mekou.GameEngine.*;

public class EditorFrame extends JFrame {
    private Scene scene;
    private JList<GameObject> objectList;
    private DefaultListModel<GameObject> listModel;

    public EditorFrame(Scene scene) {
        this.scene = scene;
        this.setTitle("MEKOU ENGINE EDITOR");
        this.setSize(400, 800);
        this.setLayout(new BorderLayout());

        // 1. オブジェクト一覧を表示するリスト
        listModel = new DefaultListModel<>();
        refreshObjectList(); // シーンからデータをぶんどる
        
        objectList = new JList<>(listModel);
        // リストに表示する名前をカスタマイズ（クラス名など）
        objectList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof GameObject) {
                    setText(value.getClass().getSimpleName() + " (z:" + ((GameObject)value).getZ() + ")");
                }
                return this;
            }
        });

        this.add(new JScrollPane(objectList), BorderLayout.WEST);

        // 2. 右側には「選んだやつの詳細」を出すパネル（今は空でOK）
        JPanel propertyPanel = new JPanel();
        propertyPanel.add(new JLabel("詳細設定はここ"));
        this.add(propertyPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    // シーン内の全オブジェクトをリストに流し込む
    public void refreshObjectList() {
        listModel.clear();
        for (GameObject obj : scene.getObjects()) {
            listModel.addElement(obj);
        }
    }
}
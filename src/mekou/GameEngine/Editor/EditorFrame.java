package mekou.GameEngine.Editor;

import javax.swing.*;
import java.awt.BorderLayout;
import mekou.GameEngine.*;

public class EditorFrame extends JFrame {
    private static EditorFrame instance;
    private Scene scene;
    private JList<GameObject> objectList;
    private DefaultListModel<GameObject> listModel;
    private JPanel propertyPanel;

    private EditorFrame(Scene scene) {
        this.scene = scene;
        this.setTitle("MEKOU ENGINE EDITOR");
        this.setSize(400, 800);
        this.setLayout(new BorderLayout());

        // 1. オブジェクト一覧を表示するリスト
        listModel = new DefaultListModel<>();
        refreshObjectList();
        
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

        objectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
            GameObject selected = objectList.getSelectedValue();
                if (selected != null) {
                    updatePropertyPanel(selected);
                }
            }
        });

        this.add(new JScrollPane(objectList), BorderLayout.WEST);

        // 2. 右側には「選んだやつの詳細」を出すパネル（今は空でOK）
        propertyPanel = new JPanel();
        propertyPanel.add(new JLabel("property"));
        this.add(propertyPanel, BorderLayout.CENTER);

        this.setVisible(true);

        Timer timer = new Timer(1000, e -> {
            System.out.println("Delayed Refresh: Objects Count = " + scene.getObjects().size());
            refreshObjectList();
            this.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    // シーン内の全オブジェクトをリストに流し込む
    public void refreshObjectList() {
        listModel.clear();
        for (GameObject obj : scene.getObjects()) {
            listModel.addElement(obj);
        }
    }

    private void updatePropertyPanel(GameObject obj) {
        propertyPanel.removeAll();
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));

        // 名前を表示
        propertyPanel.add(new JLabel("Type: " + obj.getClass().getSimpleName()));
        
        propertyPanel.add(Box.createVerticalStrut(10));
        
        propertyPanel.add(new JLabel("X Position:"));

        // X座標編集用のテキストフィールド
        JTextField xField = new JTextField(String.valueOf(obj.getX()));
        xField.addActionListener(e -> {
            try {
                obj.setX(Float.parseFloat(xField.getText())); // 入力したら即ワープ！
            } catch (NumberFormatException ex) {
                xField.setText(String.valueOf(obj.getX()));
            }
        });
        propertyPanel.add(xField);
        
        propertyPanel.revalidate();
        propertyPanel.repaint();
    }

    public void update() {
        if (scene.needRefresh()) {
            refreshObjectList();
            scene.setNeedRefresh(false); // 更新したからフラグを下ろす
        }
    }

    public static EditorFrame getInstance(Scene scene){
        if(instance == null){
            instance = new EditorFrame(scene);
        }else{
            instance.setScene(scene);
        }
        return instance;
    }


    public static EditorFrame getInstance(){
        if(instance == null){
            System.out.println("not yet Initalize");
        }
        return instance;
    }

    public void setScene(Scene newScene){
        this.scene = newScene;
        refreshObjectList();
    }
}
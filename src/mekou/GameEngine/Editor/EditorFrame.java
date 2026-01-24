package mekou.GameEngine.Editor;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import mekou.Entities.*;
import mekou.GameEngine.*;

public class EditorFrame extends JFrame {
    private static EditorFrame instance;
    private Scene scene;
    private JList<GameObject> objectList;
    private DefaultListModel<GameObject> listModel;
    private JPanel propertyPanel;
    private JTree objectTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    private EditorFrame(Scene scene) {
        this.scene = scene;
        this.setTitle("MEKOU ENGINE EDITOR");
        this.setSize(400, 800);
        this.setLayout(new BorderLayout());

        JButton createButton = new JButton("createEntitirys");

        createButton.addActionListener(e -> {
            JPopupMenu menu = new JPopupMenu();
            
            // 例：敵を追加
            menu.add(new JMenuItem("Add Enemy")).addActionListener(al -> {
                Enemy enemy = new Enemy(100, 100); 
                scene.createObject(enemy);
                scene.setNeedRefresh(true);
            });

            // 例：地面を追加
            menu.add(new JMenuItem("Add Ground")).addActionListener(al -> {
                Ground g = new Ground(0, 500);
                scene.createObject(g);
                scene.setNeedRefresh(true);
            });
            menu.add(new JMenuItem("Add SceneTriger")).addActionListener(al -> {
                SceneTriger st = new SceneTriger(0, 500);
                scene.createObject(st);
                scene.setNeedRefresh(true);
            });
            menu.add(new JMenuItem("Add Decal")).addActionListener(al -> {
                Decal d = new Decal(0, 500);
                scene.createObject(d);
                scene.setNeedRefresh(true);
            });

            menu.show(createButton, 0, createButton.getHeight());
        });

        JButton playButton = new JButton("▶ Play");
        playButton.addActionListener(e ->{
            boolean nextState = !scene.isRunning();
            scene.setRunning(nextState);
            playButton.setText(nextState ? "■ Stop" : "▶ Play");
        });

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(createButton);
        toolbar.add(playButton);
        this.add(toolbar, BorderLayout.NORTH);

        rootNode = new DefaultMutableTreeNode("Scene Root");
        treeModel = new DefaultTreeModel(rootNode);
        objectTree = new JTree(treeModel);

        refreshObjectList();
        
        objectTree.addTreeSelectionListener(e -> {
    // 選択されたノードを取得
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) objectTree.getLastSelectedPathComponent();
            
            if (selectedNode != null) {
                Object userObj = selectedNode.getUserObject();
                
                // Rootノード（"Scene Root"という文字列）ではなく、GameObjectなら更新
                if (userObj instanceof GameObject) {
                    updatePropertyPanel((GameObject) userObj);
                }
            }
        });

        objectTree.setCellRenderer(new javax.swing.tree.DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
                Object userObj = ((DefaultMutableTreeNode) value).getUserObject();
                if (userObj instanceof GameObject) {
                    setText(userObj.getClass().getSimpleName());
                }
                return this;
            }
        });

        this.add(new JScrollPane(objectTree), BorderLayout.WEST);

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

    public void refreshObjectList() {
        rootNode.removeAllChildren();
        
        // 1. 全オブジェクトのノードを一旦作成してマップに保持（親子関係を組むため）
        java.util.Map<GameObject, DefaultMutableTreeNode> nodeMap = new java.util.HashMap<>();
        for (GameObject obj : scene.getObjects()) {
            nodeMap.put(obj, new DefaultMutableTreeNode(obj));
        }

        // 2. 親子関係に基づいてツリーを構築
        for (GameObject obj : scene.getObjects()) {
            DefaultMutableTreeNode currentNode = nodeMap.get(obj);
            // GameObject に getParent() が実装されている想定
            GameObject parent = obj.getParent(); 
            
            if (parent != null && nodeMap.containsKey(parent)) {
                nodeMap.get(parent).add(currentNode); // 親ノードの下に追加
            } else {
                rootNode.add(currentNode); // 親がいなければルート直下
            }
        }
        
        treeModel.reload();
    }

    private void updatePropertyPanel(GameObject obj) {
        propertyPanel.removeAll();
        // 縦に並べるけど、各行は横並びのパネルを入れる
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
        propertyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. タイトル
        JLabel title = new JLabel("OBJECT: " + obj.getClass().getSimpleName());
        title.setFont(title.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        propertyPanel.add(title);
        propertyPanel.add(Box.createVerticalStrut(15));

        // 2. 座標編集（ラベルとフィールドを1行にまとめるヘルパーメソッドを呼ぶ想定）
        propertyPanel.add(createFieldRow("X Pos:", obj.getX(), val -> obj.setX(val)));
        propertyPanel.add(createFieldRow("Y Pos:", obj.getY(), val -> obj.setY(val)));
        propertyPanel.add(createFieldRow("Z Pos:", obj.getZ(), val -> obj.setZ(val)));

        JButton changeImgBtn = new JButton("Select Image");
            changeImgBtn.addActionListener(e -> {
                JFileChooser fc = new JFileChooser("src/mekou/img");
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getPath();
                    // obj.loadNewImage(path); みたいな処理
                }
        });
        
        // 3. スケール（将来用）
        propertyPanel.add(Box.createVerticalStrut(10));
        propertyPanel.add(createFieldRow("Scale:", 1.0f, val -> { /* obj.setScale(val) */ }));

        propertyPanel.revalidate();
        propertyPanel.repaint();
    }

    private JPanel createFieldRow(String labelText, float initialValue, java.util.function.Consumer<Float> onCommit) {
        JPanel row = new JPanel(new BorderLayout(5, 5));
        row.setMaximumSize(new java.awt.Dimension(400, 30)); // 縦に伸びすぎないように固定
        
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new java.awt.Dimension(60, 20));
        
        JTextField field = new JTextField(String.valueOf(initialValue));
        field.addActionListener(e -> {
            try {
                onCommit.accept(Float.parseFloat(field.getText()));
                scene.setNeedRefresh(true); // 変更があったらリフレッシュ
            } catch (Exception ex) {
                field.setText("err");
            }
        });

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
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
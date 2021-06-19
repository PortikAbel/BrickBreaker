package Game;

import Main.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class PlayPanel extends JPanel {
    private final GamePanel gamePanel;
    private final JPanel buttonsPanel;

    public PlayPanel(){
        setLayout(new BorderLayout());
        setOpaque(false);

        gamePanel = new GamePanel(new File("./src/resources/levels/level"));
        add(gamePanel, BorderLayout.NORTH);
        gamePanel.requestFocus();

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setOpaque(false);
        add(buttonsPanel,BorderLayout.SOUTH);

        MyButton btnLoad = new MyButton("LOAD");
        btnLoad.addActionListener(
                e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(
                            new File("./src/resources/levels"));
                    int ret = fileChooser.showOpenDialog(null);
                    if(ret == JFileChooser.APPROVE_OPTION){
                        gamePanel.setNewLevel(fileChooser.getSelectedFile());
                    }
                    gamePanel.requestFocusInWindow();
                }
        );
        buttonsPanel.add(btnLoad);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                gamePanel.requestFocusInWindow();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                gamePanel.transferFocus();
            }
        });
    }

    public void addButtonBack(MyButton btnBack) { buttonsPanel.add(btnBack,FlowLayout.LEFT); }
}

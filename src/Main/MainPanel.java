package Main;

import Game.PlayPanel;
import LevelCreating.CreatePanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel(){
        Dimension dimension = new Dimension(600,700);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);

        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        PlayPanel playPanel = new PlayPanel();
        CreatePanel createPanel = new CreatePanel();
        JPanel helpPanel = new JPanel();
        helpPanel.setOpaque(false);

        MainMenuPanel mainMenuPanel = new MainMenuPanel();

        add(mainMenuPanel,"M");
        add(helpPanel,"H");
        add(playPanel,"P");
        add(createPanel,"C");


        mainMenuPanel.btnPlay.addActionListener(
                e -> cardLayout.show(MainPanel.this,"P")
        );
        mainMenuPanel.btnCreate.addActionListener(
                e -> cardLayout.show(MainPanel.this,"C")
        );
        mainMenuPanel.btnHelp.addActionListener(
                e -> cardLayout.show(MainPanel.this,"H")
        );

        MyButton[] btnBack = new MyButton[3];
        for(int i = 0; i < 3; i++){
            btnBack[i] = new MyButton("BACK");
            btnBack[i].addActionListener(
                    e -> cardLayout.show(MainPanel.this,"M")
            );
        }
        playPanel.addButtonBack(btnBack[0]);
        createPanel.addButtonBack(btnBack[1]);
        helpPanel.add(btnBack[2]);
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(255,255,255,200));
        g.fillRect(0,0,getWidth(),getHeight());
        super.paintComponent(g);
    }

    private static class MainMenuPanel extends JPanel {
        private final MyButton btnPlay;
        private final MyButton btnCreate;
        private final MyButton btnHelp;

        public MainMenuPanel()
        {
            setLayout(new GridLayout(3,1,0,50));
            setOpaque(false);

            btnPlay = new MyButton("PLAY");
            btnCreate = new MyButton("CREATE");
            btnHelp = new MyButton("HELP");

            add(btnPlay,0);
            add(btnCreate,1);
            add(btnHelp,2);
        }
    }
}

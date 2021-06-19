package Main;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
    public MyButton(String text){
        super(text);
        setPreferredSize(new Dimension(200, 50));
        setForeground(Color.BLACK);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        setFont(new Font("Arial",Font.BOLD,30));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(isFocusOwner()){
            g.setColor(new Color(255,255,255,100));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
}

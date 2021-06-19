package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new ContentPanel());
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class ContentPanel extends JPanel{
        private BufferedImage background;

        public ContentPanel() {
            Box box = new Box(BoxLayout.Y_AXIS);
            box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
            box.add(new MainPanel());
            add(box, BorderLayout.CENTER);

            try {
                background = ImageIO.read(getClass().getResource("/resources/images/background.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background,0,0,getWidth(),getHeight(),null);
        }

    }

    public static void main(String[] args){
        new MainFrame("Brick Breaker Game");
    }
}

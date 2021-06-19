package LevelCreating;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class BrickButtons extends JButton {
    private final BufferedImage img;

    public BrickButtons(BufferedImage img) {
        super();
        this.img = img;
        setPreferredSize(new Dimension(50, 25));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, 50, 25, null);
    }
}

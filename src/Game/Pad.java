package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pad {
    private final GamePanel field;
    private final BufferedImage img;

    private int posX;
    private int widened = 0;

    public Pad(GamePanel field) {
        this.field = field;
        posX = 0;
        BufferedImage img1 = null;
        try {
            img1 = ImageIO.read(getClass().getResource("/resources/images/pad.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = img1;
    }

    public int getPosX() {
        return posX * field.getWidth() / field.getPreferredSize().width + field.getWidth()/2;
    }
    public int getWidth() {
        return (100 + (widened > 0 ? 50 : 0)) *
                field.getWidth() /
                field.getPreferredSize().width;
    }
    private int getHeight() {
        return 20 *
                field.getHeight() /
                field.getPreferredSize().height;
    }

    /** moving the pad in left direction */
    public void moveLeft() {
        posX -= 5 * field.getWidth() / field.getPreferredSize().width;
        posX = Math.max(posX, -field.getPreferredSize().width/2);
    }
    /** moving the pad in right direction */
    public void moveRight() {
        posX += 5 * field.getWidth() / field.getPreferredSize().width;
        posX = Math.min(posX, field.getPreferredSize().width/2);
    }

    public void widen() {widened++;}
    public void narrow() {widened--;}

    /** return the Rectangle which is the are of the pad on the game field */
    public Rectangle getArea(){
        return new Rectangle(
                getPosX() - getWidth()/2,
                field.getHeight() - getHeight(),
                getWidth(), getHeight()
        );
    }

    /** draw the pad */
    public void draw(Graphics g) {
        g.drawImage(
                img,
                getPosX() - getWidth()/2,
                field.getHeight() - getHeight(),
                getWidth(), getHeight(),
                null
        );
    }
}

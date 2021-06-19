package Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ball {
    private final GamePanel field;
    private final BufferedImage img;

    private double posX, posY;
    private double dirX = 0.0, dirY = 5.0;

    private final Clip soundPad;
    private final Clip soundBrick;

    public Ball(GamePanel field) {
        this.field = field;
        posX = 0.0;
        posY = 0.0;

        BufferedImage img1 = null;
        try {
            img1 = ImageIO.read(getClass().getResource("/resources/images/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = img1;

        Clip sound1 = null;
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/resources/sounds/bouncePad.wav"));
            sound1 = AudioSystem.getClip();
            sound1.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        soundPad = sound1;
        sound1 = null;
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/resources/sounds/bounceBrick.wav"));
            sound1 = AudioSystem.getClip();
            sound1.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        soundBrick = sound1;
    }

    public double getPosX() {
        return field.getWidth() * (0.5 + posX / field.getPreferredSize().width);
    }
    public void setPosX(double x) {
        posX = (x / field.getWidth() - 0.5) * field.getPreferredSize().width;
    }
    public double getPosY() {
        return field.getHeight() * (1.0 - (40.0 + posY) / field.getPreferredSize().height);
    }
    public int getR() {
        return 10 *
                field.getHeight() /
                field.getPreferredSize().height;
    }

    public void move() {
        posX += field.getWidth() * dirX / field.getPreferredSize().width;
        posY += field.getHeight() * dirY / field.getPreferredSize().height;
    }

    public void bouncePad(double angle) {
        dirX += angle;
        dirX = Math.min(dirX, 5.0);
        dirX = Math.max(dirX, -5.0);
        dirY = -dirY;
        play(soundPad);
    }

    public void bounceVert() {
        dirY = -dirY;
        play(soundBrick);
    }

    public void bounceHor() {
        dirX = -dirX;
        play(soundBrick);
    }

    private void play(Clip sound) {
        sound.setFramePosition(0);
        sound.start();
    }

    public Ellipse2D.Double getArea() {
        return new Ellipse2D.Double(
                getPosX() - getR(),
                getPosY() - getR(),
                2 * getR(), 2 * getR()
        );
    }

    public void draw(Graphics g) {
        g.drawImage(img,
                (int) Math.round(getPosX() - getR()),
                (int) Math.round(getPosY() - getR()),
                2 * getR(), 2 * getR(),
                null
        );
    }
}

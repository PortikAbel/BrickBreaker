package Game;

import java.awt.*;

public class WidenPadBonus extends Thread implements Bonus {
    private final GamePanel field;
    private final Pad pad;
    private final int posX;
    private int posY;

    public WidenPadBonus(GamePanel field, Pad pad, int posX, int posY){
        this.field = field;
        this.pad = pad;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void move() {
        posY += field.getHeight() * dirY / field.getPreferredSize().height;
    }

    public int getPosX() {
        return field.getWidth() * posX / field.getPreferredSize().width;
    }
    @Override
    public int getPosY() {
        return field.getHeight() * posY / field.getPreferredSize().height;
    }

    private int getWidth(){
        return field.getWidth() * sideLen / field.getPreferredSize().width;
    }
    private int getHeight(){
        return field.getHeight() * sideLen / field.getPreferredSize().height;
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(
                getPosX() - getWidth()/2,
                getPosY() - getHeight()/2,
                getWidth(),
                getHeight()
        );
    }

    public int getType() {
        return 2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fill3DRect(
                getPosX() - getWidth()/2,
                getPosY() - getHeight()/2,
                getWidth(),
                getHeight(),
                true
        );
    }

    @Override
    public void run() {
        pad.widen();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pad.narrow();
    }
}

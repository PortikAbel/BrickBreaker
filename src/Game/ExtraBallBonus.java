package Game;

import java.awt.*;

public class ExtraBallBonus implements Bonus{
    private final GamePanel field;
    private final int posX;
    private int posY;

    public ExtraBallBonus(GamePanel field, int posX, int posY){
        this.field = field;
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
        return 1;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fill3DRect(
                getPosX() - getWidth()/2,
                getPosY() - getHeight()/2,
                getWidth(),
                getHeight(),
                true
        );
    }
}

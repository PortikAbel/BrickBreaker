package Game;

import java.awt.*;

public interface Bonus {
    int dirY = 3;
    int sideLen = 20;

    void move();
    int getPosX();
    int getPosY();
    Rectangle getArea();
    int getType();
    void draw(Graphics g);
}

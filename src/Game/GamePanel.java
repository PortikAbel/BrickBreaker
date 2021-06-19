package Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private static final int prefWidth = 500;
    private static final int prefHeight = 500;

    private BrickGrid brickGrid;
    private ArrayList<Ball> balls;
    private ArrayList<Bonus> bonuses;
    private Pad pad;

    private final GameControl gameControl;

    public GamePanel(File levelIn) {
        setPreferredSize(new Dimension(prefWidth,prefHeight));
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);
        setOpaque(false);

        balls = new ArrayList<>();
        bonuses = new ArrayList<>();

        gameControl = new GameControl(this,levelIn);
    }

    public void setBalls(ArrayList<Ball> balls) { this.balls = balls; }
    public void setBonuses(ArrayList<Bonus> bonuses) { this.bonuses = bonuses; }
    public void setBrickGrid(BrickGrid brickGrid){ this.brickGrid = brickGrid; }
    public void setPad(Pad pad){ this.pad = pad; }
    public void setNewLevel(File newLevel){ gameControl.setLevel(newLevel); }

    public void paint(Graphics g) {
        super.paint(g);

        //draw background
        g.setColor(new Color(0,0,0,80));
        g.fill3DRect(0,0,getWidth(),getHeight(),false);

        // draw game elements
        balls.forEach(ball -> ball.draw(g));
        brickGrid.draw(g);
        pad.draw(g);
        bonuses.forEach(bonus -> bonus.draw(g));

        if (gameControl.lost()) {
            g.setColor(new Color(0,0,0,160));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.RED);
            g.drawString("GAME OVER", getWidth()/2, getHeight()/2);
        }
        if (gameControl.won()) {
            g.setColor(new Color(0,0,0,160));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.GREEN);
            g.drawString("YOU WIN", getWidth()/2, getHeight()/2);
        }

        // print score
        g.setColor(Color.GREEN);
        g.drawString("Score: " + gameControl.getScore(), 10, getHeight()-20);
    }
}

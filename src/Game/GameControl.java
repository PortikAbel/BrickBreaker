package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameControl implements ActionListener, KeyListener {
    private final GamePanel view;
    private final Random random;

    private final BrickGrid brickGrid;
    private ArrayList<Ball> balls;
    private ArrayList<Bonus> bonuses;
    private Pad pad;

    private File levelInput;

    private int score;
    private int totalBricks;

    private boolean inGame = false;
    private boolean left = false, right = false;

    public GameControl(GamePanel gamePanel, File levelInput) {
        view = gamePanel;
        random = new Random();
        this.levelInput = levelInput;

        view.addKeyListener(this);

        brickGrid = new BrickGrid(view,levelInput);
        restart();

        // start timer
        Timer t = new Timer(5, this);
        t.start();
    }

    private void restart(){
        score = 0;

        pad = new Pad(view);
        balls = new ArrayList<>();
        balls.add(new Ball(view));
        bonuses = new ArrayList<>();
        brickGrid.loadLevel(levelInput);

        totalBricks = (int) Arrays.stream(brickGrid.getBricks())
                .flatMapToInt(Arrays::stream)
                .filter(b -> b>0)
                .count();

        view.setPad(pad);
        view.setBalls(balls);
        view.setBrickGrid(brickGrid);
        view.setBonuses(bonuses);
        view.validate();
        view.repaint();

        inGame = false;
    }
    public void setLevel(File levelInput){
        this.levelInput = levelInput;
        restart();
    }
    public int getScore() { return score; }
    public boolean won() { return totalBricks == 0; }
    public boolean lost() { return totalBricks > 0 && balls.size() == 0; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && view.isFocusOwner()) {
            int[][] bricks = brickGrid.getBricks();

            // move the pad
            if(left)
                pad.moveLeft();
            if(right)
                pad.moveRight();

            Rectangle padArea = pad.getArea();

            // move the balls
            balls.forEach(Ball::move);
            // eliminate balls which fall out
            balls.removeIf(ball -> ball.getPosY() > view.getHeight());

            if(balls.size() == 0) {
                inGame = false;
                view.repaint();
                return;
            }

            for (Ball ball : balls) {
                // check if ball bounces from a wall
                if (ball.getPosX() - ball.getR() < 0
                        || ball.getPosX() + ball.getR() > view.getWidth()) {
                    ball.bounceHor();
                }
                if (ball.getPosY() < 0) {
                    ball.bounceVert();
                }

                Ellipse2D ballArea = ball.getArea();
                // check if ball bounces from the pad
                if (ballArea.intersects(padArea)){
                    ball.bouncePad(
                            (ball.getPosX() - pad.getPosX())
                                    / (pad.getWidth() / 2.0)
                    );
                }

                // check if ball breaks a brick
                int brickWidth = view.getWidth() / bricks[0].length;
                int brickHeight = view.getHeight() / bricks.length / 3;
                for(int i = 0; i < bricks.length; i++){
                    for (int j = 0; j < bricks[i].length; j++){
                        if (bricks[i][j] > 0) {
                            Rectangle brick =
                                    new Rectangle(
                                            j * brickWidth, i * brickHeight,
                                            brickWidth, brickHeight);

                            if (ballArea.intersects(brick)) {
                                brickGrid.hitBrick(i,j);

                                if (ball.getPosY() >= brick.y + brick.height) {
                                    ball.bounceVert();
                                } else if (ball.getPosX() <= brick.x || ball.getPosX() >= brick.x + brick.width) {
                                    ball.bounceHor();
                                } else if (ball.getPosY() <= brick.y) {
                                    ball.bounceVert();
                                }

                                if(bricks[i][j] == 0) {
                                    totalBricks--;
                                    switch (random.nextInt(5)) {
                                        case 0 -> bonuses.add(new ExtraBallBonus(view, j * brickWidth, i * brickHeight));
                                        case 1 -> bonuses.add(new WidenPadBonus(view, pad, j * brickWidth, i * brickHeight));
                                    }
                                }
                                score += 10 * (6 - bricks[i][j]);
                                break;
                            }
                        }
                    }
                }
            }


            // move the bonuses
            bonuses.forEach(Bonus::move);
            // eliminate bonuses which fall out
            bonuses.removeIf(bonus -> bonus.getPosY() > view.getHeight());

            for (Bonus bonus : bonuses) {
                if ((bonus.getArea()).intersects(padArea)){
                    if(bonus.getType() == 1){
                        Ball ball = new Ball(view);
                        ball.setPosX(bonus.getPosX());
                        balls.add(ball);
                    } else if (bonus.getType() == 2) {
                        ((WidenPadBonus)bonus).start();
                    }
                }
            }
            // eliminate cached bonuses
            bonuses.removeIf(bonus -> (bonus.getArea()).intersects(padArea));

            if(totalBricks == 0){
                inGame = false;
            }

            view.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // moving the pad if there are balls
        if(inGame) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = true;
            }
        }

        // restarting game
        else if (e.getKeyCode() == KeyEvent.VK_ENTER){
            restart();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            inGame = !inGame;
        }
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

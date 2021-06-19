package LevelCreating;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class BricksPanel extends JPanel {
    private static final int prefWidth = 500;
    private static final int prefHeight = 250;

    private static final int rows = 10;
    private static final int cols = 10;

    private final BufferedImage[] imgBrick;
    private final int[][] brickMap;

    public BricksPanel(){
        setPreferredSize(new Dimension(prefWidth,prefHeight));
        setOpaque(false);
        brickMap = new int[rows][cols];

        imgBrick = initImg();
    }

    public int[][] getBrickMap(){
        return brickMap;
    }

    public void setBrick(int x, int y, int value){
        brickMap[y*rows/getHeight()][x*cols/getWidth()] = value;
    }
    public void clear() { Arrays.stream(brickMap).forEach(row -> Arrays.fill(row,0)); }

    public BufferedImage[] initImg(){
        BufferedImage imgBricks = null;
        try {
            imgBricks = ImageIO.read(getClass().getResource("/resources/images/brick_set.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage[] imgBrick = new BufferedImage[7];
        for(int i = 0; i < 7; i++){
            if (imgBricks != null) {
                imgBrick[i] = imgBricks.getSubimage(
                        0,
                        imgBricks.getHeight()/7*i,
                        imgBricks.getWidth(),
                        imgBricks.getHeight()/7
                );
            }
        }
        return imgBrick;
    }

    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                g.drawImage(
                        imgBrick[brickMap[i][j]],
                        getWidth()/cols*j,
                        getHeight()/rows*i,
                        getWidth()/cols,
                        getHeight()/rows,
                        null);
            }
        }
    }
}
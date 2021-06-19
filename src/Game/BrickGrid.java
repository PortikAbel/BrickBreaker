package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class BrickGrid {
    private final BufferedImage[] imgBrick;
    private int[][] bricks;
    private final GamePanel view;

    public BrickGrid(GamePanel view, File levelInput) {
        this.view = view;
        loadLevel(levelInput);
        imgBrick = initImg();
    }

    public void loadLevel(File levelInput) {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(levelInput));
            bricks = in.lines()
                    .map(line -> Arrays.stream(line.split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toArray(int[][]::new);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int[][] getBricks() {
        return bricks;
    }
    public void hitBrick(int i, int j){
        bricks[i][j]--;
    }

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

    public void draw(Graphics g){
        int brickWidth = view.getWidth() / bricks[0].length;
        int brickHeight = view.getHeight() / bricks.length / 3;
        for(int i = 0; i < bricks.length; i++){
            for (int j = 0; j < bricks[i].length; j++){
                if(bricks[i][j] > 0) {
                    g.drawImage(
                            imgBrick[bricks[i][j]],
                            brickWidth * j,
                            brickHeight * i,
                            brickWidth,
                            brickHeight,
                            null);
                }
            }
        }
    }
}

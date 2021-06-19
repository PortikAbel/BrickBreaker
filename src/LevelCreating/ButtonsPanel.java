package LevelCreating;

import Main.MyButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ButtonsPanel extends JPanel {
    private final MyButton saveButton;
    private final MyButton clearButton;
    private final BrickButtons[] brickSelectButton;

    public ButtonsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);

        saveButton = new MyButton("SAVE");
        add(saveButton, BorderLayout.EAST);

        clearButton = new MyButton("CLEAR");
        add(clearButton, BorderLayout.CENTER);

        brickSelectButton = initSelectButtons();
        JPanel selectButtonsPanel = new JPanel();
        selectButtonsPanel.setOpaque(false);
        selectButtonsPanel.setLayout(new FlowLayout());
        for (JButton button : brickSelectButton) {
            selectButtonsPanel.add(button);
        }
        add(selectButtonsPanel, BorderLayout.NORTH);
    }

    public JButton getSaveButton() { return saveButton; }
    public MyButton getClearButton() { return clearButton; }
    public BrickButtons[] getBrickSelectButton() { return brickSelectButton; }


    public BrickButtons[] initSelectButtons() {
        BufferedImage imgBricks = null;
        try {
            imgBricks = ImageIO.read(getClass().getResource("/resources/images/brick_set.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BrickButtons[] btnSelect = new BrickButtons[6];
        for (int i = 0; i < 6; i++) {
            if (imgBricks != null) {
                btnSelect[i] = new BrickButtons(
                        imgBricks.getSubimage(
                                0,
                                imgBricks.getHeight() / 7 * (i + 1),
                                imgBricks.getWidth(),
                                imgBricks.getHeight() / 7
                        )
                );
            }
        }
        return btnSelect;
    }
}

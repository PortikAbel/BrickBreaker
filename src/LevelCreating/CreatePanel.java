package LevelCreating;

import Main.MyButton;

import javax.swing.*;
import java.awt.*;

public class CreatePanel extends JPanel {
    private final ButtonsPanel ctrlButtons;

    public CreatePanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        BricksPanel view = new BricksPanel();
        add(view, BorderLayout.NORTH);
        view.requestFocus();

        ctrlButtons = new ButtonsPanel();
        add(ctrlButtons,BorderLayout.SOUTH);

        new CreateControl(view,ctrlButtons);
    }

    public void addButtonBack(MyButton btnBack){
        ctrlButtons.add(btnBack,BorderLayout.WEST);
    }
}

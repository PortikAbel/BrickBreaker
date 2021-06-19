package LevelCreating;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateControl implements ActionListener, MouseListener {
    private final BricksPanel view;
    private final ButtonsPanel ctrlButtons;

    private int brickType;

    public CreateControl(BricksPanel panel, ButtonsPanel buttonsPanel) {
        view = panel;
        ctrlButtons = buttonsPanel;

        view.addMouseListener(this);
        ctrlButtons.getSaveButton().addActionListener(this);
        ctrlButtons.getClearButton().addActionListener(this);
        for(int i = 0; i < 6; i++) {
            ctrlButtons.getBrickSelectButton()[i].addActionListener(this);
        }

        brickType = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(ctrlButtons.getSaveButton())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./src/resources/levels"));
            int ret = fileChooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
                    out.write(
                            Arrays.stream(view.getBrickMap())
                                    .map(line -> Arrays
                                            .stream(line)
                                            .mapToObj(Integer::toString)
                                            .collect(Collectors.joining(" "))
                                    )
                                    .collect(Collectors.joining("\n"))
                    );
                    out.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        if(e.getSource().equals(ctrlButtons.getClearButton())) {
            view.clear();
            view.repaint();
        }
        for(int i = 0; i < 6; i++) {
            if (e.getSource().equals(ctrlButtons.getBrickSelectButton()[i])){
                brickType = i+1;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(view)) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                view.setBrick(e.getX()-view.getX(), e.getY()-view.getY(), brickType);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                view.setBrick(e.getX()-view.getX(), e.getY()-view.getY(), 0);
            }
        }
        view.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource().equals(view)) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                view.setBrick(e.getX()-view.getX(), e.getY()-view.getY(), brickType);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                view.setBrick(e.getX()-view.getX(), e.getY()-view.getY(), 0);
            }
        }
        view.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

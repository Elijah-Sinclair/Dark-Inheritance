import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public GameWindow() {
        setTitle("Dark Inheritance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel(WIDTH, HEIGHT);
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);

        gamePanel.startGameThread();
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
    private Thread gameThread;

    private GameState gameState;
    private InputHandler input;

    private static final int TARGET_FPS = 60;


    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);

        setFocusable(true);

        gameState = new GameState(input)
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void loadAssets() {

    }

    private void initWorld(){
        //
    }

    @Override
    public void run() {
        while (true) {
            gameState.update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (Exception e) {}
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Camera cam = gameState.getCamera();

        gameState.getPlayer().draw(g, cam);

//      for (Entity e : gameState.getEntities()) {
//          e.draw()
//        }

//      for (Projectile p : gameState.getProjectiles()) {
//          e.draw()
//        }
    }
}

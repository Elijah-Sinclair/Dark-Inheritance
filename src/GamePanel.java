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
    private HUD hud;

    private BufferedImage stage1BG;
    private BufferedImage bossBG;

    private static final int TARGET_FPS = 60;


    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        input = new InputHandler();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);

        setFocusable(true);

        gameState = new GameState(input);
        hud = new HUD(gameState);

        try {
            stage1BG = javax.imageio.ImageIO.read(getClass().getResource("/stage1.png"));
            bossBG = javax.imageio.ImageIO.read(getClass().getResource("/bossStage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        drawBackground(g, cam);

        gameState.getPlayer().draw(g, cam);

        for (Entity e : gameState.getEntities()) {
          e.draw(g, cam);
        }

        for (Projectile p : gameState.getProjectiles()) {
          p.draw(g, cam);
        }

        hud.draw(g);
    }

    private void drawBackground(Graphics g, Camera cam) {

        if (gameState.getStage() == GameState.StageType.STAGE1) {

            int tileW = stage1BG.getWidth();
            int tileH = stage1BG.getHeight();

            int startX = -cam.getOffsetX() % tileW;
            int startY = -cam.getOffsetY() % tileH;

            for (int x = startX - tileW; x < getWidth(); x += tileW) {
                for (int y = startY - tileH; y < getHeight(); y += tileH) {
                    g.drawImage(stage1BG, x, y, null);
                }
            }

        } else {
            // Boss stage (fixed)
            g.drawImage(bossBG, 0, 0, getWidth(), getHeight(), null);
        }
    }
}

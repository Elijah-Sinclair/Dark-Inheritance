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
            stage1BG = ImageManager.loadImage("/stage1.png");
            bossBG = ImageManager.loadImage("/bossStage.png");
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
            if (gameState.isGameOver() || gameState.isVictory()) {
                if (input.restartPressed) {
                    gameState = new GameState(input);
                    hud = new HUD(gameState);
                }
            }
            gameState.update(getWidth(), getHeight());
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

        for (Entity e : gameState.getEntities()) {
          e.draw(g, cam);
        }

        for (Projectile p : gameState.getProjectiles()) {
          p.draw(g, cam);
        }

        gameState.getPlayer().draw(g, cam);

        gameState.getFxManager().draw(g, cam);

        hud.draw(g);

        if (gameState.isGameOver()) {
            drawEndScreen(g, "YOU DIED", Color.RED);
        } else if (gameState.isVictory()) {
            drawEndScreen(g, "BOSS DEFEATED", Color.GREEN);
        }
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

    private void drawEndScreen(Graphics g, String text, Color color) {
        Graphics2D g2 = (Graphics2D) g;

        // Dark overlay
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Text
        g2.setColor(color);
        g2.setFont(new Font("Arial", Font.BOLD, 48));

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight() / 2;

        g2.drawString(text, x, y);

        // Subtext
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Press R to Restart", x - 40, y + 40);
    }
}

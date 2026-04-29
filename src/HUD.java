import java.awt.*;

public class HUD {

    private GameState gameState;

    public HUD(GameState gameState) {
        this.gameState = gameState;
    }

    public void draw(Graphics g) {

        Player player = gameState.getPlayer();

        drawHealthBar(g, player);
        drawScore(g);
        drawWave(g);
    }

    private void drawHealthBar(Graphics g, Player player) {

        int x = 10;
        int y = 10;
        int width = 200;
        int height = 20;

        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();

        double ratio = (double) health / maxHealth;
        int filledWidth = (int)(width * ratio);

        // Background (empty health)
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, width, height);

        // Current health
        g.setColor(Color.GREEN);
        g.fillRect(x, y, filledWidth, height);

        // Border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString("Score: " + gameState.getScore(), 10, 50);
    }

    private void drawWave(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.drawString("Wave: " + gameState.getWave(), 10, 70);
    }
}

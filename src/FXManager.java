import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FXManager {

    private List<Effect> effects = new ArrayList<>();

    private int shakeTimer = 0;
    private int shakeStrength = 0;
    private Random rand = new Random();

    public void addEffect(Effect e) {
        effects.add(e);
    }

    public void update() {
        for (Effect e : effects) {
            e.update();
        }
        effects.removeIf(e -> !e.isAlive());

        if (shakeTimer > 0) shakeTimer--;
    }

    public void draw(Graphics g, Camera cam) {
        Graphics2D g2 = (Graphics2D) g;

        int shakeX = 0;
        int shakeY = 0;

        if (shakeTimer > 0) {
            shakeX = rand.nextInt(shakeStrength * 2) - shakeStrength;
            shakeY = rand.nextInt(shakeStrength * 2) - shakeStrength;
        }

        g2.translate(shakeX, shakeY);

        for (Effect e : effects) {
            e.draw(g2, cam);
        }

        g2.translate(-shakeX, -shakeY);
    }

    public void triggerShake(int duration, int strength) {
        shakeTimer = duration;
        shakeStrength = strength;
    }
}
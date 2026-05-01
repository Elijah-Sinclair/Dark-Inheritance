import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FXManager {

    private List<Effect> effects = new ArrayList<>();

    public void addEffect(Effect e) {
        effects.add(e);
    }

    public void update() {
        for (Effect e : effects) {
            e.update();
        }
        effects.removeIf(e -> !e.isAlive());
    }

    public void draw(Graphics g, Camera cam) {
        for (Effect e : effects) {
            e.draw(g, cam);
        }
    }
}
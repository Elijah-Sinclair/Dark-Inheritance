
import java.util.*;

public class GameState {

    private Player player;
    private List<Entity> entities;
    private List<Projectile> projectiles;

    private  FXManager fxManager;
    public Camera camera;
    private InputHandler input;

    private int score;
    private int wave;

    public GameState(InputHandler input) {
        this.input = input;

        player = new Player(400, 300);

        entities = new ArrayList<>();
        projectiles = new ArrayList<>();

        fxManager = new FXManager();
        camera = new Camera();

        score = 0;
        wave = 1;

        spawnEnemies();
    }

    public void update() {

        player.update(this);

        camera.centerOn(player, 800, 600);

        for (Entity e : entities) {
            e.update(this);
        }

        for (Projectile p : projectiles) {
            p.update(this);
        }

        handleCollisions();

//        fxManager.update();

        //Cleanup
        entities.removeIf(e -> !e.isAlive());
        projectiles.removeIf(p -> !p.isAlive());

        handleProgression();
    }

    private void handleProgression() {
        //Placeholder logic
        if (score > 200 * wave) {
            wave++;
            spawnEnemies();
        }
    }

    private void spawnEnemies() {
        //TBA Testing Usage
        for (int i = 0; i < 5; i++) {
            entities.add(new BasicGrunt(Math.random() * 1000, Math.random() * 1000));
        }
    }

    //Add projectile
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    public void addProjectiles(List<Projectile> list) {
        projectiles.addAll(list);
    }

    //Collision Handling nightmare
    private void handleCollisions() {

        //Projectiles
        for (Projectile p : projectiles) {

            if (!p.isAlive()) continue;

            //Player projectiles → enemies
            if (p.getOwner().equals("player")) {
                for (Entity e : entities) {
                    if (e instanceof Enemy enemy && e.isAlive()) {

                        if (p.getBounds().intersects(enemy.getBounds())) {
                            enemy.takeDamage(p.getDamage());
                            p.alive = false;
                            break;
                        }
                    }
                }
            }

            //Enemy projectiles → player
            if (p.getOwner().equals("enemy")) {
                if (p.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(p.getDamage());
                    p.alive = false;
                }
            }
        }

        //Enemy contact damage → player
        for (Entity e : entities) {
            if (e instanceof Enemy enemy && e.isAlive()) {

                if (enemy.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(enemy.getDamage());
                }
            }
        }

        //Hazards → player
        for (Entity e : entities) {
            if (e instanceof Hazard hazard && e.isAlive()) {

                if (hazard.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(hazard.getDamage());
                }
            }
        }
    }

    //Getters
    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public FXManager getFxManager() {
        return fxManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public InputHandler getInput() {
        return input;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int amount) {
        score += amount;
    }
}

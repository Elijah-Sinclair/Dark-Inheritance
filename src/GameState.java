
import java.util.*;

public class GameState {

    private Player player;
    private List<Entity> entities;
    private List<Projectile> projectiles;

    private  FXManager fxManager;
    public Camera camera;
    private InputHandler input;

    private int score = 0;
    private int wave = 1;
    private int nextWaveScore = 100; //Adjust till it feels good

    public enum StageType {
        STAGE1,
        BOSS
    }
    private StageType currentStage = StageType.STAGE1;

    private Boss boss;
    private boolean bossSpawned = false;

    //Spawn Variables
    private int spawnTimer = 0;
    private int spawnInterval = 120;

    public GameState(InputHandler input) {
        this.input = input;

        player = new Player(400, 300);

        entities = new ArrayList<>();
        projectiles = new ArrayList<>();

        fxManager = new FXManager();
        camera = new Camera();
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
        handleSpawning();
    }

    private void handleProgression() {
        //Placeholder logic
        if (score >= nextWaveScore) {
            wave++;
            nextWaveScore += 100 + (wave * 50);
            spawnWaveBurst();
        }

        //Boss Transition
        if (wave >= 10 && currentStage == StageType.STAGE1) {
            transitionToBossStage();
        }



//        if (score > 200 * wave) {
//            wave++;
//        }
    }

    //Experimental spawn mechanics

    public void spawnWaveBurst() {
        int count = 1 + wave;

        for (int i = 0; i < count; i++) {
            spawnEnemy();
        }
    }

    private void handleSpawning() {
        if (currentStage == StageType.BOSS) return;

        spawnTimer--;

        if (spawnTimer <= 0) {
            spawnEnemy();

            //Faster spawning as waves increase
            spawnInterval = Math.max(30, 120 - (wave * 10));
            spawnTimer = spawnInterval;
        }
    }

    private void spawnEnemy() {
        //TBA Testing Usage
        double x = player.worldX + (Math.random() * 800 - 400);
        double y = player.worldY + (Math.random() * 800 - 400);

        int roll = (int)(Math.random() * 100);
        for (int i = 0; i < 5; i++) {
            entities.add(new BasicGrunt(Math.random() * 1000, Math.random() * 1000));
        }

        if (wave < 3) {
            entities.add(new BasicGrunt(x, y));
        }
        else if (wave < 6) {
            if (roll < 70) entities.add(new BasicGrunt(x, y));
            else entities.add(new BasicShooter(x, y));
        }
        else {
            if (roll < 50) entities.add(new BasicGrunt(x, y));
            else if (roll < 80) entities.add(new BasicShooter(x, y));
            else entities.add(new BasicBerserker(x, y));
        }

    }

    private void transitionToBossStage() {
        currentStage = StageType.BOSS;

        entities.clear();
        projectiles.clear();

        spawnBoss();
    }

    private void spawnBoss() {
        boss = new Boss(player.worldX + 200, player.worldY);
        entities.add(boss);
        bossSpawned = true;
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
                            if (!enemy.isAlive()) {
                                score += enemy.getScoreValue();
                            }

                            p.alive = false;//Score calculation may need tuning
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
    public Player getPlayer() { return player; }
    public List<Entity> getEntities() { return entities; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public FXManager getFxManager() { return fxManager; }
    public Camera getCamera() { return camera; }
    public InputHandler getInput() { return input; }
    public int getScore() { return score; }
    public void addScore(int amount) { score += amount; }
    public int getWave() { return wave; }
    public StageType getStage() { return currentStage; }
    public boolean isBossStage() { return currentStage == StageType.BOSS; }
}

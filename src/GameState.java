
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameState {

    private Player player;
    private List<Entity> entities;
    private List<Entity> pendingEntities = new ArrayList<>();
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

    //Boss stage and spawning var
    private Boss boss;
    private boolean bossSpawned = false;

    //Boss arena var
    private int arenaWidth = 1200;
    private int arenaHeight = 900;

    //Spawn Variables
    private int spawnTimer = 0;
    private int spawnInterval = 120;

    private boolean gameOver = false;
    private boolean victory = false;

    public GameState(InputHandler input) {
        this.input = input;

        player = new Player(400, 300);

        entities = new ArrayList<>();
        projectiles = new ArrayList<>();

        fxManager = new FXManager();
        camera = new Camera();
        SoundManager sm = SoundManager.getInstance();
        sm.playClip("Stage1BGM", true);
    }

    public void update(int screenWidth, int screenHeight) {

        if (gameOver || victory) return;

        player.update(this);

        camera.centerOn(player, screenWidth, screenHeight);

        for (Entity e : entities) {
            e.update(this);
        }

        for (Projectile p : projectiles) {
            p.update(this);
        }

        handleCollisions();

        fxManager.update();

        //Cleanup
        entities.removeIf(e -> !e.isAlive());
        projectiles.removeIf(p -> !p.isAlive());

        handleProgression();
        handleSpawning();
        entities.addAll(pendingEntities);
        pendingEntities.clear();
        if (isBossStage()) {
            camera.centerOn(boss, screenWidth, screenHeight);
        } else {
            camera.centerOn(player, screenWidth, screenHeight);
        }

        if (!player.isAlive()) {
            gameOver = true;
            SoundManager sm = SoundManager.getInstance();
            sm.stopClip("Stage1BGM");
            sm.stopClip("StageBossBGM");
            sm.playClip("Lose", false);
        }

        if (boss != null && !boss.isAlive()) {
            fxManager.addEffect(new ExplosionEffect(boss.worldX, boss.worldY));

            // BIG burst
            for (int i = 0; i < 50; i++) {
                double angle = Math.random() * Math.PI * 2;
                double speed = Math.random() * 6;

                fxManager.addEffect(new ParticleEffect(
                        boss.worldX,
                        boss.worldY,
                        Math.cos(angle) * speed,
                        Math.sin(angle) * speed,
                        4,
                        40,
                        Color.ORANGE
                ));
            }

            fxManager.triggerShake(40, 10);
            victory = true;
            SoundManager sm = SoundManager.getInstance();
            sm.stopClip("Stage1BGM");
            sm.stopClip("StageBossBGM");
            sm.playClip("Win", false);
        }
    }

    public void addEntity(Entity e) {
        pendingEntities.add(e);
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

        player.worldX = arenaWidth / 2.0;
        player.worldY = arenaHeight / 2.0;
        camera.setBounds(arenaWidth, arenaHeight); // adjust to center properly

        spawnBoss();
        SoundManager sm = SoundManager.getInstance();

        sm.stopClip("Stage1BGM");
        sm.playClip("StageBossBGM", true);
    }

    private void spawnBoss() {
        boss = new Boss(arenaWidth / 2.0 - 64, arenaHeight / 2.0 - 200, player);
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
                                fxManager.addEffect(new ExplosionEffect(enemy.worldX, enemy.worldY));
                                fxManager.triggerShake(15, 6);
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
                    if (p.getDamage() == 0) {
                        double dx = player.worldX - p.worldX;
                        double dy = player.worldY - p.worldY;

                        double len = Math.sqrt(dx * dx + dy * dy);
                        if (len != 0) {
                            dx /= len;
                            dy /= len;
                        }

                        player.applyForce(dx * 12, dy * 12); // tweak strength
                    } else {
                        player.takeDamage(p.getDamage());
                    }
                    p.alive = false;
                }
            }
        }

        //Enemy contact damage → player
        for (Entity e : entities) {
            if (e instanceof Enemy enemy && e.isAlive()) {

                if (enemy.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(enemy.getDamage());
                    this.getFxManager().addEffect(new HitEffect(player.worldX, player.worldY));
                    this.getFxManager().triggerShake(10, 4);

                }
            }
        }

        //Hazards → player
        for (Entity e : entities) {
            if (e instanceof Hazard hazard && e.isAlive()) {

                if (hazard.getBounds().intersects(player.getBounds())) {
                    player.takeDamage(hazard.getDamage());
                    this.getFxManager().addEffect(new HitEffect(player.worldX, player.worldY));
                    this.getFxManager().triggerShake(10, 4);

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
    public int getArenaWidth() { return arenaWidth; }
    public int getArenaHeight() { return arenaHeight; }
    public boolean isGameOver() { return gameOver; }
    public boolean isVictory() { return victory; }
}

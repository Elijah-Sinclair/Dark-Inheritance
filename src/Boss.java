import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss extends Enemy{

    private enum State {
        IDLE,
        ENERGY_RING,
        CLAW_ATTACK,
        HORN_SPIKE
    }

    private State currentState = State.IDLE;

    private int stateTimer = 0;
    private int attackCooldown = 120;
    private int phase = 1;

    //
    private int animFrame = 0;
    private int animSpeed = 8;
    private int animCounter = 0;

    private Player player;
    private Random rand = new Random();

    //spritesheets
    private BufferedImage[][] idleAnimation = ImageManager.loadSpriteSheet("/Boss_IdleB.png", 1, 6);
    private BufferedImage[][] clawAnimation = ImageManager.loadSpriteSheet("/Boss_ClawA.png", 1, 6);
    private BufferedImage[][] ringAnimation = ImageManager.loadSpriteSheet("/Boss_EnergyA.png", 1, 7);
    private BufferedImage[][] hornAnimation = ImageManager.loadSpriteSheet("/Boss_HornA.png", 1, 7);


    public Boss(double x, double y, Player player) {
        worldX = x;
        worldY = y;
        this.player = player;

        //Tweak with the sprite and gameplay till it feels right
        health = 500;
        damage = 20;
        speed = 0; //May end up being stationary boss


        scoreValue = 2500;

        width = 128;
        height = 128;

        stateTimer = attackCooldown;
    }


    @Override
    public void update(GameState state) {

        if (!alive) return;

        // Phase scaling
        if (health < 200) phase = 2;

        stateTimer--;

        switch (currentState) {

            case IDLE -> {
                if (stateTimer <= 0) {
                    chooseNextAttack();
                }
            }

            case ENERGY_RING -> {
                if (stateTimer == 30) {
                    spawnPushOrbs(state);
                }
                if (stateTimer <= 0) resetToIdle();
            }

            case CLAW_ATTACK -> {
                if (stateTimer == 25) {
                    if (isPlayerClose(state)) {
                        meleeHit(state);
                    } else {
                        fireBounceProjectiles(state);
                    }
                }
                if (stateTimer <= 0) resetToIdle();
            }

            case HORN_SPIKE -> {
                if (stateTimer == 30) {
                    spawnEdgeHazards(state);
                }
                if (stateTimer <= 0) resetToIdle();
            }
        }

        updateAnimation();

//        Player p = state.getPlayer();
//
//        double dx = p.worldX - worldX;
//        double dy = p.worldY - worldY;
//
//        double len = Math.sqrt(dx * dx + dy * dy);
//
//        if (len != 0) {
//            dx /= len;
//            dy /= len;
//        }
//
//        // Movement (slow tracking)
//        worldX += dx * speed;
//        worldY += dy * speed;
//
//        // Phase change
//        if (health < 100) phase = 2;
//
//        attackTimer--;
//
//        if (attackTimer <= 0) {
//            //Insert attack method
//            attackTimer = (phase == 1) ? 60 : 30;
//        }


    }

    private void chooseNextAttack() {
        int choice = rand.nextInt(5);

        switch (choice) {
            case 0 -> startAttack(State.ENERGY_RING, 60);
            case 1 -> startAttack(State.CLAW_ATTACK, 50);
            case 2 -> startAttack(State.ENERGY_RING, 60);
            case 3 -> startAttack(State.CLAW_ATTACK, 50);
            case 4 -> startAttack(State.HORN_SPIKE, 60);
        }
    }

    private void startAttack(State state, int duration) {
        currentState = state;
        stateTimer = duration;
        animFrame = 0;
        animCounter = 0;
    }

    private void resetToIdle() {
        currentState = State.IDLE;
        stateTimer = attackCooldown / phase; //Phase 2 does not exist due to the amount of work and limited time :(
    }

    //Attacks
    private void spawnPushOrbs(GameState state) {
        int count = (phase == 1) ? 18 : 29;

        for (int i = 0; i < count; i++) {
            Player p = state.getPlayer();

            Player player = state.getPlayer();

            double dx = player.worldX - worldX;
            double dy = player.worldY - worldY;

            double len = Math.sqrt(dx * dx + dy * dy);

            dx /= len;
            dy /= len;

            dx += (rand.nextDouble() - 0.5) * 0.9;
            dy += (rand.nextDouble() - 0.5) * 0.9;
            dx *= 6;
            dy *= 6;

            state.addProjectile(new Projectile(
                    worldX + (double) width / 2,
                    worldY + (double) height / 2,
                    dx, dy,
                    0,
                    "enemy"
            ));
        }
    }

    private void fireBounceProjectiles(GameState state) {
        int count = (phase == 1) ? 12 : 13;

        for (int i = 0; i < count; i++) {

            Player player = state.getPlayer();

            double dx = player.worldX - worldX;
            double dy = player.worldY - worldY;

            double len = Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;

            dx += (rand.nextDouble() - 0.5) * 0.7;
            dy += (rand.nextDouble() - 0.5) * 0.7;
            dx *= 8;
            dy *= 8;


            Projectile p = new Projectile(
                    worldX + (double) width / 2,
                    worldY + (double) height / 2,
                    dx, dy,
                    damage,
                    "enemy"
            );
            p.setBounce(true);
            state.addProjectile(p);

        }
    }


    private void meleeHit(GameState state) {
        Player p = state.getPlayer();

        if (getBounds().intersects(p.getBounds())) {
            p.takeDamage(damage * 2);
        }
    }

    private void spawnEdgeHazards(GameState state) {
        int spacing = 80;

        int w = state.getArenaWidth();
        int h = state.getArenaHeight();

        double left = player.worldX - w / 2.0;
        double top = player.worldY - h / 2.0;

        // Top & Bottom
        for (int x = 0; x < w; x += spacing) {
            state.addEntity(new Hazard(x, 0));
            state.addEntity(new Hazard(x, h - 20));
        }

        // Left & Right
        for (int y = 0; y < h; y += spacing) {
            state.addEntity(new Hazard(0, y));
            state.addEntity(new Hazard(w - 20, y));
        }
    }

    private boolean isPlayerClose(GameState state) {
        Player p = state.getPlayer();

        double dx = p.worldX - worldX;
        double dy = p.worldY - worldY;

        return Math.sqrt(dx * dx + dy * dy) < 100;
    }

    //Animation ...Please dont break
    private void updateAnimation() {
        animCounter++;

        if (animCounter >= animSpeed) {
            animCounter = 0;
            animFrame++;
        }
    }

    private BufferedImage getCurrentFrame() {
        BufferedImage[][] anim;

        switch (currentState) {
            case ENERGY_RING -> anim = ringAnimation;
            case CLAW_ATTACK -> anim = clawAnimation;
            case HORN_SPIKE -> anim = hornAnimation;
            default -> anim = idleAnimation;
        }

        return anim[0][animFrame % anim[0].length];
    }


    @Override
    public void draw(Graphics g, Camera cam) {
        int x = cam.getScreenX(worldX);
        int y = cam.getScreenY(worldY);

        g.drawImage(getCurrentFrame(), x, y, width, height, null);

//        g.setColor(Color.GREEN);
//        g.fillRect(x, y, width, height);
    }

}

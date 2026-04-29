public class Enemy extends Entity{
    protected int health;
    protected int speed;
    protected int damage;

    protected int scoreValue;

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) {
            alive = false;
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}

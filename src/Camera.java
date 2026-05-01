
public class Camera {
    private int offsetX, offsetY;
    private int worldWidth;
    private int worldHeight;
    private boolean bounded = false;

    public void centerOn(Entity target, int screenWidth, int screenHeight) {
        offsetX = (int)(target.worldX + target.width / 2.0 - screenWidth / 2.0);
        offsetY = (int)(target.worldY + target.height / 2.0 - screenHeight / 2.0);

        if (bounded) {
            offsetX = Math.max(0, Math.min(offsetX, worldWidth - screenWidth));
            offsetY = Math.max(0, Math.min(offsetY, worldHeight - screenHeight));
        }    }

    public void setBounds(int width, int height) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.bounded = true;
    }

    public void clearBounds() {
        bounded = false;
    }

    public int getScreenX(double worldX) {
        return (int)(worldX - offsetX);
    }

    public int getScreenY(double worldY) {
        return (int)(worldY - offsetY);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}

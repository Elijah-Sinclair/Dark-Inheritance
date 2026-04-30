
public class Camera {
    private int offsetX, offsetY;
    private int worldWidth;
    private int worldHeight;
    private boolean bounded = false;

    public void centerOn(Entity target, int screenWidth, int screenHeight) {
        offsetX = (int)target.worldX - screenWidth/2;
        offsetY = (int)target.worldY - screenHeight/2;

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


public class Camera {
    private int offsetX, offsetY;

    public void centerOn(Entity target, int width, int height) {
        offsetX = (int)target.worldX - width/2;
        offsetY = (int)target.worldY - height/2;
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

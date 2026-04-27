
public class Camera {
    private int offsetX, offsetY;

    public void centerOn(Entity target, int screenWidth, int screenHeight) {
        offsetX = (int)target.worldX - screenWidth/2;
        offsetY = (int)target.worldY - screenHeight/2;
    }

    public int getScreenX(double worldX) {
        return (int)(worldX - offsetX);
    }

    public int getScreenY(double worldY) {
        return (int)(worldY - offsetY);
    }
}

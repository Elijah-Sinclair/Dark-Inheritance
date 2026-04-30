import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {

    private static final HashMap<String, BufferedImage> imageCache = new HashMap<>();

    //Load single image (cached)
    public static BufferedImage loadImage(String path) {

        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        try {
            System.out.println("Loading: " + path);
            BufferedImage img = ImageIO.read(ImageManager.class.getResource(path));
            System.out.println("Loaded: " + (img != null));
            imageCache.put(path, img);
            return img;
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
            e.printStackTrace();
            return null;
        }

    }

    //Slice spritesheet into animation frames
    public static BufferedImage[][] loadSpriteSheet(String path, int rows, int cols) {

        BufferedImage sheet = loadImage(path);
        if (sheet == null) return null;

        int frameWidth = sheet.getWidth() / cols;
        int frameHeight = sheet.getHeight() / rows;

        BufferedImage[][] result = new BufferedImage[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                BufferedImage frame = sheet.getSubimage(
                        col * frameWidth,
                        row * frameHeight,
                        frameWidth,
                        frameHeight
                );
                result[row][col] = frame;
            }
        }


        return result;
    }

}
package main;

import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Util {
    //// Window Settings
    private static final int originalTileSize = 16;

    // 64x64 Tile
    public static final int scale = 4;
    public static final int tileSize = originalTileSize * scale;

    private static final int columns = 20;
    private static final int rows = 12;

    // 1280x768 Window -> 16:9 Aspect Ratio
    public static final int windowX = tileSize * columns;
    public static final int windowY = tileSize * rows;

    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(original, 0, 0, width, height, null);
        return scaledImage;
    }

    public static int[] getStringCenterPosition(Graphics2D g2d, String msg) {
        int stringLength = (int) g2d.getFontMetrics().getStringBounds(msg, g2d).getWidth();
        int stringHeight = (int) g2d.getFontMetrics().getStringBounds(msg, g2d).getHeight();
        int x = Util.windowX / 2 - stringLength / 2;
        int y = Util.windowY / 2 + stringHeight / 2;
        return new int[]{x, y};
    }

    public static void loadAnimatedTile(Tile tile, String tilePath) {
        try {
            tile.image = Util.scaleImage(ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream(tilePath + "_1.png"))), Util.tileSize, Util.tileSize);
            tile.animationFrames = new BufferedImage[3];
            tile.animationFrames[0] = Util.scaleImage(ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream(tilePath + "_2.png"))), Util.tileSize, Util.tileSize);
            tile.animationFrames[1] = Util.scaleImage(ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream(tilePath + "_3.png"))), Util.tileSize, Util.tileSize);
            tile.animationFrames[2] = Util.scaleImage(ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream(tilePath + "_4.png"))), Util.tileSize, Util.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

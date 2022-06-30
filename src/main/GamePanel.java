package main;

import entity.Player;
import handler.CollisionHandler;
import handler.KeyHandler;
import handler.MapHandler;
import handler.ObjectHandler;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //// WINDOW SETTINGS
    final int originalTileSize = 16;

    // 64x64 TILE
    final int scale = 4;
    public final int tileSize = originalTileSize * scale;

    final int columns = 16;
    final int rows = 12;

    // 1152x768 WINDOW -> 4:3 ASPECT RATIO
    public final int windowX = tileSize * columns;
    public final int windowY = tileSize * rows;

    final int FPS = 60;

    //// INSTANTIATE
    Thread gameThread;
    public KeyHandler keyH = new KeyHandler();
    public MapHandler map = MapHandler.mapIsland;
    public CollisionHandler collisionH = new CollisionHandler(this);

    public Player player = new Player(this);
    public TileManager tileM = new TileManager(this);
    public ObjectHandler objHandler = new ObjectHandler(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(windowX, windowY));
        this.setBackground(Color.black);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        startGameThread();
    }
    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // GAME LOOP
    @Override
    @SuppressWarnings("all")
    public void run() {
        double timePerFrame = 1000000000d / FPS; // In nanoseconds
        double endFrameTime = System.nanoTime() + timePerFrame;

        while (gameThread != null) {
            update(); // Step 1: Update data

            repaint(); // Step 2: Repaint screen

            try { // Step 3: Maintain stable FPS
                double remainingTime = endFrameTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // Change to milliseconds

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                endFrameTime += timePerFrame;

            } catch (InterruptedException e) {e.printStackTrace();}

        }
    }
    void update() {
        player.update();

    }
    void draw(Graphics2D g2d) {
        tileM.draw(g2d);
        objHandler.draw(g2d);
        player.draw(g2d);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        draw(g2d);

        g2d.dispose();
    }
}
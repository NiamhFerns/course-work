import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class WorldComponent extends JComponent {

    public static final int GRID_SIZE = 50;
    private static final int ANIMATION_DELAY = 20;
    private static final int WORLD_UPDATE_DELAY = 33;
    private BufferedImage fuelImage;
    private World world;
    private Timer timer;

    private int tick = 0;

    public WorldComponent() {
        super();
        world = new World();
        setPreferredSize(new Dimension(600, 600));

        try {
            fuelImage = ImageIO.read(new File(RoboGame.ASSET_DIRECTORY+"fuel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        timer = new Timer();
        timer.schedule(new AnimationTask(), 0, ANIMATION_DELAY);
        world.start();
    }

    public void reset() {
        if (timer != null)
            timer.cancel();
        world.reset();
        world = new World();
    }

    public void loadRobotProgram(int rob, File code) {
        world.loadRobotProgram(rob, code);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i <= World.SIZE; i++) {
            Line2D hline = new Line2D.Double(0, i * GRID_SIZE, 600, i * GRID_SIZE);
            Line2D vline = new Line2D.Double(i * GRID_SIZE, 0, i * GRID_SIZE, 600);
            g2d.draw(hline);
            g2d.draw(vline);
        }

        for (Point fuel : world.getAvailableFuel()) {
            int x = fuel.x * GRID_SIZE + GRID_SIZE / 2 - fuelImage.getWidth() / 2;
            int y = fuel.y * GRID_SIZE + GRID_SIZE / 2 - fuelImage.getHeight() / 2;
            g2d.drawImage(fuelImage, x, y, null);
        }

        for (int i = 1; i <= 2; i++) {
            Robot rob;
            if ((rob = world.getRobot(i)) != null)
                rob.draw(g2d, getTimeRatio());
        }
    }

    /**
     * what fraction of the current "frame" are we at
     * (a frame lasts WORLD_UPDATE_DELAY ticks)
     */
    private double getTimeRatio() {
        int base = tick / WORLD_UPDATE_DELAY;
        return (double) tick / WORLD_UPDATE_DELAY - base;  
    }

    private class AnimationTask extends TimerTask {

        public AnimationTask() {
            tick = 0;
        }

        @Override
        public void run() {
            // test for any deaths
            boolean r1dead = world.getRobot(1).isDead();
            boolean r2dead = world.getRobot(2).isDead();
            if (r1dead || r2dead) {
                timer.cancel();
                timer = null;
                String msg = (r1dead && r2dead) ? "Both robots" : r1dead ? "Robot 1 (red)" : "Robot 2 (blue)";
                JOptionPane.showMessageDialog(null, msg + " ran out of fuel!");
                return;
            }

            tick++;
            if (tick % WORLD_UPDATE_DELAY == 0)
                world.updateWorld();
            repaint();
        }
    }
}

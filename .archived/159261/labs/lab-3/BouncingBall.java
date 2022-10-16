import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class BouncingBall extends JPanel implements KeyListener {
	// -------------------------------------------------------
	// Useful Functions for Drawing things on the screen
	// -------------------------------------------------------

	// My Definition of some colors
	Color black = Color.BLACK;
	Color red = Color.RED;
	Color blue = Color.BLUE;
	Color green = Color.GREEN;
	Color white = Color.WHITE;

	// Changes the background Color to the color c
	public void changeBackgroundColor(Graphics g, Color c) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(c);
	}

	// Changes the background Color to the color (red,green,blue)
	public void changeBackgroundColor(Graphics g, int red, int green, int blue) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(new Color(red,green,blue));
	}

	// Clears the background, makes the whole window whatever the background color is
	public void clearBackground(Graphics g, int width, int height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, width, height);
	}

	// Changes the drawing Color to the color c
	public void changeColor(Graphics g, Color c) {
		g.setColor(c);
	}

	// Changes the drawing Color to the color (red,green,blue)
	public void changeColor(Graphics g, int red, int green, int blue) {
		g.setColor(new Color(red,green,blue));
	}

	// This function draws a rectangle at (x,y) with width and height
	void drawRectangle(Graphics g, double x, double y, double width, double height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(new Rectangle2D.Double(x, y, width, height));
	}

	// This function fills a rectangle at (x,y) with width and height
	void drawSolidRectangle(Graphics g, double x, double y, double width, double height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(new Rectangle2D.Double(x, y, width, height));
	}

	// This function draws a rectangle at (x,y) with width and height
	void drawCircle(Graphics g, double x, double y, double radius) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}

	// This function draws a rectangle at (x,y) with width and height
	void drawSolidCircle(Graphics g, double x, double y, double radius) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}

	 // Functions to Draw Text on a window
    // Takes a Graphics g, position (x,y) and some text
    public void drawText(Graphics g, double x, double y, String s) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString(s, (int)x, (int)y);
    }

    // Translate Function, moves the drawing context
    // by (x,y)
    public void translate(Graphics g, int x, int y) {
    	Graphics2D g2d = (Graphics2D)g;
    	g2d.translate(x,y);
    }

    // Rotate Function, rotates the drawing context by angle
    public void rotate(Graphics g, double angle) {
    	Graphics2D g2d = (Graphics2D)g;
    	g2d.rotate(Math.toRadians(angle));
    }

    AffineTransform transform = null;
    public void saveTransform(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
    	transform = g2d.getTransform();
    }

    //Restores the last transform
    public void restoreTransform(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
    	if(transform != null) {
    		g2d.setTransform(transform);
    	}
    }

    // Converts an integer to a string
    public String intToString(int i) {
        return new Integer(i).toString();
    }

    // Converts an float to a string
    public String floatToString(float f) {
        return new Float(f).toString();
    }

	// Function to create the window and display it
	public void setupWindow(int width, int height) {
		JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setLocation(200,200);
        frame.setTitle("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.addKeyListener(this);
        setDoubleBuffered(true);

        // Resize the window (insets are just the boards that the Operating System puts on the board)
        Insets insets = frame.getInsets();
        frame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
	}

	// Returns the time in milliseconds
	public long getTime() {
        return System.currentTimeMillis();
    }

	// Waits for ms milliseconds
	public void sleep(double ms) {
		try {
			Thread.sleep((long)ms);
		} catch(Exception e) {
			// Do Nothing
		}
	}

	// Main function that takes care of some Object Oriented stuff
	public static void main(String args[]) {
		BouncingBall w = new BouncingBall();
	}

	// Very simple way of controlling the framerate
	// Always sleep for the same amount of time
	// regardless of how fast the program is running
	public double simpleFramerate(double framerate) {
		// Time between frames in milliseconds
		double interval = 1000.0 / framerate;

		// Sleep for interval milliseconds
		sleep(interval);

		// Return 'dt' as a fraction of a second
		return interval / 1000.0;
	}

	private double currentFrameTime = 0.0, previousFrameTime = 0.0;

	public double fixedFramerate(double framerate) {
		double interval = 1000.0 / framerate; // At 60fps = 16.6666...

		double dt = 1 / framerate; // At 60fps = 0.01666...

		if (previousFrameTime == 0) previousFrameTime = System.nanoTime();
		currentFrameTime = System.nanoTime();

		double deltaTime = currentFrameTime - previousFrameTime;
		deltaTime /= 1_000_000;
		System.out.println("Delta Time: " + deltaTime + " nanoTime(): " + (currentFrameTime - previousFrameTime));

		if (interval > deltaTime) sleep(interval - deltaTime);
		else if (deltaTime > interval) dt  = deltaTime / 1_000.0;

		previousFrameTime = System.nanoTime();

		return dt;
	}

	// -------------------------------------------------------
	// Your Program
	// -------------------------------------------------------
	boolean colorBlue = false, colorBlack = true;
	double x = 250, y = 250, vX = 100, vY = 100, accelX = 0, accelY = 0;
	public BouncingBall() {
		// Create a window of size 500x500
		setupWindow(500,500);

		while(true) {
			// Control the framerate (30 fps)
			double dt = fixedFramerate(120);

			// Update the Game
			update(dt);

			// Tell the window to paint itself
			repaint();
		}
	}

	// Updates the display
	public void update(double dt) {
		// This function updates your game
		accelY += 9.81;

		vY += accelY * dt;

		x += vX * dt;
		y += vY * dt;
		// System.out.println(dt);
		CheckBoundry();
	}

	private void CheckBoundry() {
		if (x + 20 >= 500) {
			x = 500 - 20;
		}
	}

	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent(Graphics g) {
		// Draw a white background
		changeBackgroundColor(g, white);
		clearBackground(g, 500, 500);

		if (colorBlue) {
			changeColor(g, blue);
		}
		if (colorBlack) {
			changeColor(g, black);
		}
		drawCircle(g, x, y, 20);
	}

	// Called whenever the user presses a key
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			System.out.println("B key pressed.");
			colorBlue = true;
			colorBlack = false;
		}
		else {
			colorBlack = true;
			colorBlue = false;
		}
	}

	// Called whenever the user releases a key
	public void keyReleased(KeyEvent e) {

	}

	// Called whenever the user presses and releases a key
	public void keyTyped(KeyEvent e) {

	}
}

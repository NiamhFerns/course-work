import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class SmileyDrawing extends JPanel {
	//-------------------------------------------------------
	// Useful Functions for Drawing things on the screen
	//-------------------------------------------------------

	//My Definition of some colors
	Color black = Color.BLACK;
	Color red = Color.RED;
	Color blue = Color.BLUE;
	Color green = Color.GREEN;
	Color white = Color.WHITE;

	//Changes the background Color to the color c
	public void changeBackgroundColor(Graphics g, Color c) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(c);
	}

	//Changes the background Color to the color (red,green,blue)
	public void changeBackgroundColor(Graphics g, int red, int green, int blue) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(new Color(red,green,blue));
	}

	//Clears the background, makes the whole window whatever the background color is
	public void clearBackground(Graphics g, int width, int height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, width, height);
	}

	//Changes the drawing Color to the color c
	public void changeColor(Graphics g, Color c) {
		g.setColor(c);
	}

	//Changes the drawing Color to the color (red,green,blue)
	public void changeColor(Graphics g, int red, int green, int blue) {
		g.setColor(new Color(red,green,blue));
	}

	//Functions to Draw Text on a window
	//Takes a Graphics g, position (x,y) and some text
	public void drawText(Graphics g, int x, int y, String s) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("Arial", Font.BOLD, 40));
		g2d.drawString(s, x, y);
	}

	//This function draws a line from (x1,y2) to (x2,y2)
	void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(new Line2D.Double(x1, y1, x2, y2));
	}

	//This function draws a rectangle at (x,y) with width and height
	void drawRectangle(Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(new Rectangle2D.Double(x, y, width, height));
	}

	//This function draws a rectangle at (x,y) with width and height
	void drawSolidRectangle(Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.fill(new Rectangle2D.Double(x, y, width, height));
	}

	//This function draws a circle at (x,y) with radius
	void drawCircle(Graphics g, int x, int y, double radius) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}

	//This function draws a solid circle at (x,y) with radius
	void drawSolidCircle(Graphics g, int x, int y, double radius) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.fill(new Ellipse2D.Double(x-radius, y-radius, radius*2, radius*2));
	}

	//Function to create the window and display it
	public void setupWindow(int width, int height) {
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setLocation(200,200);
		frame.setTitle("Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);

		//Resize the window (insets are just the boards that the Operating System puts on the board)
		Insets insets = frame.getInsets();
		frame.setSize(width + insets.left + insets.right,
					  height + insets.top + insets.bottom);
	}

	//Main function that takes care of some Object Oriented stuff
	public static void main(String args[]) {
		SmileyDrawing w = new SmileyDrawing();
	}

	//-------------------------------------------------------
	// Your Program
	//-------------------------------------------------------
	public SmileyDrawing() {
		setupWindow(500,500);
	}

	//This gets called any time the Operating System
	//tells the program to paint itself
	public void paintComponent(Graphics g) {
		changeBackgroundColor(g, white);
		clearBackground(g, 500, 500);

		changeColor(g, blue);
		// drawText(g, 50, 50, "Hello World");
		//
		// We'll call him fred. He's a bit dumb.
		drawSolidCircle(g, 250, 250, 220.0);
		changeColor(g, white);
		drawSolidCircle(g, 150, 200, 20.0);
		drawSolidCircle(g, 350, 200, 20.0);
		drawSolidRectangle(g, 250 - 75, 300, 150, 20);
		changeColor(g, black);
		drawSolidCircle(g, 150, 200, 7.0);
		drawSolidCircle(g, 350, 200, 7.0);

	}
}

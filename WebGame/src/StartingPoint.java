import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class StartingPoint extends Applet implements Runnable {

	private int x = 0;
	private int y = 0;
	private int dx = 1;
	private int dy = 1;
	private int radius = 20;

	@Override
	public void init() {
	}

	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			x += dx;
			y += dy;
			repaint();
			// 1000 milisegundos / 60FPS = 16.666 ms
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

}

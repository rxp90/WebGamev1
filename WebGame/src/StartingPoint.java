import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class StartingPoint extends Applet implements Runnable {

	private int x = 0;
	private int y = 0;
	private int dx = 2;
	private int dy = 1;
	private int radius = 20;
	private Image image;
	private Graphics doubleG;

	@Override
	public void init() {
		setSize(800, 600);
	}

	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void update(Graphics g) {
		// Double buffering.
		if (image == null) {
			image = createImage(this.getSize().width, this.getSize().height);
			doubleG = image.getGraphics();
		}

		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);

		doubleG.setColor(getForeground());
		paint(doubleG);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (x + dx > this.getWidth() - radius - 1) {
				x = this.getWidth() - radius - 1; // -1 porque empieza en 0
				dx = -dx;
			} else if (x + dx < 0 + radius) {
				x = 0 + radius;
				dx = -dx;
			} else {
				x += dx;
			}
			if (y + dy > this.getHeight() - radius - 1) {
				y = this.getHeight() - radius - 1; // -1 porque empieza en 0
				dy = -dy;
			} else if (y + dy < 0 + radius) {
				y = 0 + radius;
				dy = -dy;
			} else {
				y += dy;
			}
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

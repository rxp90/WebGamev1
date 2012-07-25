import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class StartingPoint extends Applet implements Runnable {

	private int x = 400;
	private int y = 25;
	private double dx = 20;
	private double dy = 0;
	private int radius = 20;
	private Image image;
	private Graphics doubleG;
	private double gravity = 15;
	private double energyLoss = .65;
	private double dt = .2;
	private double xFriction = .9;

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

			// Si está en el suelo.
			if (y == this.getHeight() - radius - 1) {
				dx *= xFriction;
				// Para detener el movimiento "residual" por el suelo.
				if (Math.abs(dx) < .8) {
					dx = 0;
					dy = 0;
				}
			}

			if (y > this.getHeight() - radius - 1) {
				y = this.getHeight() - radius - 1;
				dy *= energyLoss;
				dy = -dy;
			} else {
				// Velocidad (cinemática)
				dy += gravity * dt;
				// Posición (cinemática)
				y += dy * dt + .5 * gravity * dt * dt;
			}
			repaint();
			try {
				Thread.sleep(17); // 1000 milisegundos / 60FPS = 16.666 ms

			} catch (InterruptedException e) {
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

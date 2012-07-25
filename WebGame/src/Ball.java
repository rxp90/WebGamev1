import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private int radius = 20;
	private int x = 400;
	private int y = 25;
	private double dx = 20;
	private double dy = 0;
	private double gravity = 15;
	private double energyLoss = .65;
	private double dt = .2;
	private double xFriction = .9;

	public Ball() {
	}
	
	public Ball(int i, int j) {
		this.x = i;
		this.y = j;
	}

	public void update(StartingPoint sp) {
		if (x + dx > sp.getWidth() - radius - 1) {
			x = sp.getWidth() - radius - 1; // -1 porque empieza en 0
			dx = -dx;
		} else if (x + dx < 0 + radius) {
			x = 0 + radius;
			dx = -dx;
		} else {
			x += dx;
		}

		// Si está en el suelo.
		if (y == sp.getHeight() - radius - 1) {
			dx *= xFriction;
			// Para detener el movimiento "residual" por el suelo.
			if (Math.abs(dx) < .8) {
				dx = 0;
				dy = 0;
			}
		}

		if (y > sp.getHeight() - radius - 1) {
			y = sp.getHeight() - radius - 1;
			dy *= energyLoss;
			dy = -dy;
		} else {
			// Velocidad (cinemática)
			dy += gravity * dt;
			// Posición (cinemática)
			y += dy * dt + .5 * gravity * dt * dt;
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
}

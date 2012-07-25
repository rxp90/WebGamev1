import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	/**
	 * Radio de la bola.
	 */
	private int radius = 20;
	/**
	 * Coordenadas de posición.
	 */
	private int x = 400;
	private int y = 25;
	/**
	 * Velocidades.
	 */
	private double dx = 10;
	private double dy = 0;
	private double maxSpeed = 20;
	/**
	 * Variables para añadir realismo.
	 */
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

	public void moveRight() {
		if (dx + 1 < maxSpeed) {
			dx += 1;
		}
	}

	public void moveLeft() {
		if (dx - 1 > -maxSpeed) {
			dx -= 1;
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	// Getters and Setters.
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getEnergyLoss() {
		return energyLoss;
	}

	public void setEnergyLoss(double energyLoss) {
		this.energyLoss = energyLoss;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public double getxFriction() {
		return xFriction;
	}

	public void setxFriction(double xFriction) {
		this.xFriction = xFriction;
	}
	
	
}

import java.awt.Color;
import java.awt.Graphics;

public class Platform {

	private int dx;
	private int x, y, width, height;

	public Platform() {
		dx = -10;
		x = 300;
		y = 300;
		width = 120;
		height = 40;
	}

	public Platform(int x, int y) {
		this.x = x;
		this.y = y;
		width = 120;
		height = 40;
		dx = -10;
	}

	public void update(StartingPoint sp, Ball b) {
		checkForCollision(b);
	}

	private void checkForCollision(Ball b) {
		int ballX = b.getX();
		int ballY = b.getY();
		int radius = b.getRadius();

		if (ballY + radius > y && ballY + radius < y + height) {
			if (ballX > x && ballX < x + width) {
				// double newDY = b.getDy() * -1;// Cambio la dirección del
				// movimiento.
				double newDY = b.getGameDy(); // Uso la velocidad del juego.
				b.setY(y - radius); // Coloco la bola sobre la plataforma. De
									// no hacer esto, acabaría
									// atravesándola.
				b.setDy(newDY);
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}

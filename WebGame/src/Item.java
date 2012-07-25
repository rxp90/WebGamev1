import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Item {

	private int x, y, dx, radius;

	public Item(int x) {
		this.x = x;
		Random r = new Random();
		y = r.nextInt(400) + radius;
		radius = 10;
		dx = -2;
	}

	public void update(StartingPoint sp, Ball b) {
		x += dx; // Las plataformas se moverán a la derecha.
		checkForCollision(b);

		// Si el objeto desaparece por la izquierda lo colocaremos de nuevo
		// por la derecha de forma aleatoria.
		if (x < 0 - radius) {
			Random r = new Random();
			x = sp.getWidth() + 2000 + r.nextInt(300);
		}
	}

	private void checkForCollision(Ball b) {
		int ballX = b.getX();
		int ballY = b.getY();
		int radius = b.getRadius();
/*
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
		*/
	}

	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
}

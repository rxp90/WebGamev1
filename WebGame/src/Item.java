import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Item {

	private int x, y, dx, radius;
	private StartingPoint sp;

	public Item(int x) {
		this.x = x;
		Random r = new Random();
		y = r.nextInt(400) + radius;
		radius = 10;
		dx = -2;
	}

	public void update(StartingPoint sp, Ball b) {
		x += dx; // Las plataformas se moverán a la derecha.
		this.sp = sp;
		
		checkForCollision(b);

		// Si el objeto desaparece por la izquierda lo colocaremos de nuevo
		// por la derecha de forma aleatoria.
		if (x < 0 - radius) {
			Random r = new Random();
			x = sp.getWidth() + 2000 + r.nextInt(300);
		}
	}

	private void checkForCollision(Ball ball) {
		// Colisión mejorada con teorema de Pitágoras.
		int ballX = ball.getX();
		int ballY = ball.getY();
		int ballRadius = ball.getRadius();

		int a = x - ballX;
		int b = y - ballY;

		// Distancia a la que colisionarían.
		int collide = radius + ballRadius;

		// Distancia entre centros de los objetos.
		double c = Math.sqrt((double) (a * a) + (double) (b * b));

		if (c < collide) {
			performAction(ball);
			x = 0;
			y = sp.getHeight() + 100;
		}
	}

	public void performAction(Ball ball) {

	}

	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
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

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	// Getters and setters
	
	
}

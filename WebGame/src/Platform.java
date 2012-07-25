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

	public void update(StartingPoint sp) {
		
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}

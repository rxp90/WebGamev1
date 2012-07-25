import java.awt.Color;
import java.awt.Graphics;

public class GravUp extends Item {

	public GravUp(int x) {
		super(x);
	}

	@Override
	public void performAction(Ball ball) {
		ball.setGravity(ball.getGravity() + 3);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		super.paint(g);
	}

}

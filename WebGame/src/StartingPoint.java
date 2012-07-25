import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class StartingPoint extends Applet implements Runnable {

	private Image image;
	private Graphics doubleG;
	Ball b,b2;

	@Override
	public void init() {
		setSize(800, 600);
	}

	@Override
	public void start() {
		b = new Ball();
		b2 = new Ball(250,250);
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
			b.update(this);
			b2.update(this);
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
		b.paint(g);
		b2.paint(g);
	}

}

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartingPoint extends Applet implements Runnable, KeyListener {

	private Image image;
	private Graphics doubleG;
	private Ball b, b2;
	private Platform p;

	@Override
	public void init() {
		setSize(800, 600);
		addKeyListener(this);
	}

	@Override
	public void start() {
		b = new Ball();
		b2 = new Ball(250, 250);
		p = new Platform();
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
			p.update(this);
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
		p.paint(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			b.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			b.moveRight();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}

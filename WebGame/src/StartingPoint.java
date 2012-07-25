import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartingPoint extends Applet implements Runnable, KeyListener {

	private Image image;
	private Graphics doubleG;
	private Ball b;
	private Platform p, p2;

	@Override
	public void init() {
		setSize(800, 600);
		addKeyListener(this);
	}

	@Override
	public void start() {
		b = new Ball();
		p = new Platform();
		p2 = new Platform(100, 445);
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
			p.update(this, b);
			p2.update(this, b);
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
		p.paint(g);
		p2.paint(g);
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

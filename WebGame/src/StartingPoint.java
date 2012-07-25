import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class StartingPoint extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private Image image;
	private Graphics doubleG;
	private Ball b;
	private Platform p[] = new Platform[7];
	private Item items[] = new Item[3];

	@Override
	public void init() {
		setSize(800, 600);
		addKeyListener(this);
	}

	@Override
	public void start() {
		b = new Ball();
		for (int i = 0; i < p.length; i++) {
			Random r = new Random();
			p[i] = new Platform(getWidth() + 200 * i, getHeight() - 40
					- r.nextInt(400));
		}

		for (int i = 0; i < items.length; i++) {
			items[i] = new GravUp(getWidth() + 2000 * i);
		}

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
			
			for (int i = 0; i < items.length; i++) {
				if (items[i].getY() == this.getHeight() + 100) {
					items[i] = null;
					items[i] = new GravUp(getWidth());
				}
			}
			b.update(this);
			for (int i = 0; i < p.length; i++) {
				p[i].update(this, b);
			}
			for (int i = 0; i < items.length; i++) {
				items[i].update(this, b);
			}
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
		for (int i = 0; i < p.length; i++) {
			p[i].paint(g);
		}
		for (int i = 0; i < items.length; i++) {
			items[i].paint(g);
		}
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

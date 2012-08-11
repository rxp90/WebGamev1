package com.pfc.ball;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfc.remote.ControladorMando;

public class StartingPoint extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5022320510835636310L;
	/**
	 * Dirección del host.
	 */
	private String host = "localhost";
	/**
	 * Puerto del host.
	 */
	private int puerto = 2000;
	/**
	 * Registro del que se obtiene la interfaz remota.
	 */
	private static Registry registry;
	/**
	 * Interfaz remota.
	 */
	private ControladorMando remoteApi;

	private Image image;
	private Graphics doubleG;
	private Ball b;
	private Platform p[] = new Platform[7];
	private Item items[] = new Item[10];

	@Override
	public void init() {
		setSize(600, 400);
		addKeyListener(this);
	}

	@Override
	public void start() {
		conectaServidor();
		b = new Ball();
		for (int i = 0; i < p.length; i++) {
			Random r = new Random();
			p[i] = new Platform(getWidth() + 200 * i, getHeight() - 40
					- r.nextInt(400));
		}

		for (int i = 0; i < items.length; i++) {
			items[i] = new Item(getWidth() + 2000 * i);
		}

		Thread thread = new Thread(this);
		thread.start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (remoteApi != null) {
						try {
							System.out.println("Recibo trama");
							String trama = remoteApi.leeLinea();
							Double[] aceleraciones = getAcceleration(trama);
							if (aceleraciones.length > 0
									&& aceleraciones[0] > 0) {
								b.moveRight();
							} else {
								b.moveLeft();
							}
							// El Arduino proporciona datos cada 100 ms
							Thread.sleep(100);
						} catch (RemoteException e1) {
							Logger.getLogger(StartingPoint.class.getName())
									.log(Level.WARNING,
											"Error al conectarse al servidor: "
													+ e1.getMessage());
						} catch (NullPointerException e1) {
							Logger.getLogger(StartingPoint.class.getName())
									.log(Level.WARNING,
											"NPE: " + e1.getMessage());
						} catch (InterruptedException e) {
							Logger.getLogger(StartingPoint.class.getName())
									.log(Level.WARNING,
											"Error en Thread.sleep(): "
													+ e.getMessage());
						}
					} 
				}
			}
		}).start();

	}

	private void conectaServidor() {
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			@Override
			public Object run() {
				try {
					registry = LocateRegistry.getRegistry(host, puerto);
					remoteApi = (ControladorMando) registry
							.lookup(ControladorMando.class.getSimpleName());
				} catch (final RemoteException e) {
					registry = null;
					remoteApi = null;
					Logger.getLogger(StartingPoint.class.getName()).log(
							Level.WARNING,
							"Error al conectarse al servidor: "
									+ e.getMessage());
				} catch (final NotBoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}

	@Override
	public void run() {

		// Actualiza el estado de todos los objeto y los pinta.
		while (true) {

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

	public Double[] getAcceleration(String trama) {
		Double[] aceleraciones = new Double[3];
		if (trama != null && trama.length() > 0 && trama.startsWith("IT")
				&& trama.endsWith("FT")) {
			String datos = trama.substring(2, trama.length() - 2);
			String[] ejes = datos.split(",");
			aceleraciones[0] = Double.valueOf(ejes[0].substring(1));
			aceleraciones[1] = Double.valueOf(ejes[1].substring(1));
			aceleraciones[2] = Double.valueOf(ejes[2].substring(1));
		}
		return aceleraciones;
	}

	/*
	 * Redefinimos el método 'update' para que no borre el objeto en pantalla y
	 * evitar el parpadeo. (non-Javadoc)
	 * 
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
		conectaServidor();
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
		conectaServidor();
	}

	public boolean isConnected() {
		return registry != null && remoteApi != null;
	}
	
}

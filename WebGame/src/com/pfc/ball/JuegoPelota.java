/*package com.pfc.ball;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.pfc.remote.Api;

public class JuegoPelota extends JApplet implements Runnable {
	private int tool = 1;
	int currentX, currentY, oldX, oldY;
	*//**
	 * Dirección del host.
	 *//*
	private String host = "localhost";
	*//**
	 * Puerto del host.
	 *//*
	private int puerto = 1099;
	*//**
	 * Registro del que se obtiene la interfaz remota.
	 *//*
	private static Registry registry;
	*//**
	 * Interfaz remota.
	 *//*
	private Api remoteApi;

	private Image image;
	private Graphics doubleG;
	private Ball b;
	private Platform p[] = new Platform[7];
	private Item items[] = new Item[10];

	public JuegoPelota() {
		initComponents();
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

	private void initComponents() {
		// we want a custom Panel2, not a generic JPanel!
		jPanel2 = new Panel2();

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		try {
			registry = LocateRegistry.getRegistry(host, puerto);
			remoteApi = (Api) registry.lookup(Api.class.getSimpleName());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
					try {
						String trama = remoteApi.leeLinea();
						Double[] aceleraciones = getAcceleration(trama);
						if (aceleraciones.length > 0 && aceleraciones[0] > 0) {
							b.moveRight();
						} else {
							b.moveLeft();
						}
						// El Arduino proporciona datos cada 100 ms
						Thread.sleep(100);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NullPointerException e1) {
						System.err.println("NULLPOINTER");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		// add the component to the frame to see it!
		this.setContentPane(jPanel2);
		// be nice to testers..
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// pack();
	}// </editor-fold>

	// Variables declaration - do not modify
	private JPanel jPanel2;

	// End of variables declaration

	// This class name is very confusing, since it is also used as the
	// name of an attribute!
	// class jPanel2 extends JPanel {
	class Panel2 extends JPanel {

		Panel2() {
			// set a preferred size for the custom panel.
			setPreferredSize(new Dimension(800, 600));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawString("BLAH", 20, 20);
			g.drawRect(200, 200, 200, 200);
		}
	}

	@Override
	public void run() {
		new JuegoPelota().setVisible(true);
		while (true) {

			b.update(this);
			for (int i = 0; i < p.length; i++) {
				p[i].update(this, b);
			}
			for (int i = 0; i < items.length; i++) {
				items[i].update(this, b);
			}
			paintComponents(getGraphics());
			try {
				Thread.sleep(17); // 1000 milisegundos / 60FPS = 16.666 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
*/
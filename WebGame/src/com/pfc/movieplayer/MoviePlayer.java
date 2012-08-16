/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.movieplayer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.pfc.ball.StartingPoint;
import com.pfc.datostrama.Aceleraciones;
import com.pfc.datostrama.DatosTrama;
import com.pfc.datostrama.DatosXBee;
import com.pfc.remote.ControladorMando;

/**
 * 
 * @author Raul
 */
public class MoviePlayer extends Application {
	/**
	 * Direcci�n del host.
	 */
	private String host = "localhost";
	/**
	 * Puerto del host.
	 */
	private int puerto = 1099;
	/**
	 * Registro del que se obtiene la interfaz remota.
	 */
	private static Registry registry;
	/**
	 * Interfaz remota.
	 */
	private ControladorMando remoteApi;

	private static final String MEDIA_URL = "http://dl.dropbox.com/u/25798506/freddie.mp4";

	@Override
	public void start(final Stage primaryStage) {

		conectaServidor();

		// Título de la ventana.
		primaryStage.setTitle("Mister Freddie Mercury");

		// Creo un elemento raíz y un reproductor.
		Group root = new Group();
		Media media = new Media(MEDIA_URL);
		final MediaPlayer player = new MediaPlayer(media);

		MediaView view = new MediaView(player);

		// Animaciones para la entrada y salida del puntero.
		final Timeline slideIn = new Timeline();
		final Timeline slideOut = new Timeline();

		root.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				slideIn.play();
			}
		});
		root.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				slideOut.play();
			}
		});

		// Vertical Box para el slider de tiempo.
		final VBox vbox = new VBox();
		final Slider slider = new Slider();
		vbox.getChildren().add(slider);

		// Añado los elementos a la raíz.
		root.getChildren().add(view);
		root.getChildren().add(vbox);

		// Tamaño por defecto y muestro la ventana.
		Scene scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		player.play();

		new Thread(new Runnable() {
			@Override
			public void run() {
				Double volume = player.getVolume();
				Double min = 0.7;
				while (true) {
					if (remoteApi != null) {
						try {
							String trama = remoteApi.leeLinea();
							DatosTrama datosTrama = DatosTrama.getData(trama);

							if (datosTrama != null) {
								Double aceleracionX = datosTrama
										.getAceleraciones().getX();
								if (aceleracionX > min) {
									volume += 0.05;
								} else if (aceleracionX < -min) {
									volume -= 0.05;
								}
								if (volume > 1.0) {
									volume = 1.0;
								} else if (volume < 0.0) {
									volume = 0.0;
								}
								player.setVolume(volume);
							
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
		player.setOnReady(new Runnable() {
			@Override
			public void run() {

				// Ajusto el tamaño de la ventana al vídeo.
				double ancho = player.getMedia().getWidth();
				double alto = player.getMedia().getHeight();

				primaryStage.setMinWidth(ancho);
				primaryStage.setMinHeight(alto);
				vbox.setMinSize(ancho, 100);
				vbox.setTranslateY(alto - 100);

				// Configuro el slider de tiempo.
				slider.setMin(0.0);
				slider.setValue(0.0);
				slider.setMax(player.getTotalDuration().toSeconds());

				// Configuro las animaciones de entrada y salida.
				slideOut.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(vbox
								.translateYProperty(), alto - 100),
								new KeyValue(vbox.opacityProperty(), 0.9)),
						new KeyFrame(Duration.millis(300), new KeyValue(vbox
								.translateYProperty(), alto), new KeyValue(vbox
								.opacityProperty(), 0.0)));
				slideIn.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(vbox
								.translateYProperty(), alto), new KeyValue(vbox
								.opacityProperty(), 0.0)),
						new KeyFrame(Duration.millis(300), new KeyValue(vbox
								.translateYProperty(), alto - 100),
								new KeyValue(vbox.opacityProperty(), 0.9)));
			}
		});
		// Listener del tiempo para que se mueva el slider.
		player.currentTimeProperty().addListener(
				new ChangeListener<Duration>() {
					@Override
					public void changed(ObservableValue<? extends Duration> ov,
							Duration duration, Duration current) {
						slider.setValue(current.toSeconds());
					}
				});
		// Al hacer clic me muevo al instante de tiempo indicado.
		slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				player.seek(Duration.seconds(slider.getValue()));
			}
		});

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

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

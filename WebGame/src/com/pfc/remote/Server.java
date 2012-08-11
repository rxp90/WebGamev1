package com.pfc.remote;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Server {
	private static int PORT = 1099;
	private static Registry registry;

	public static void startRegistry() throws RemoteException {
		registry = java.rmi.registry.LocateRegistry.createRegistry(PORT);
	}

	public static void registerObject(String name, Remote remoteObj)
			throws RemoteException, AlreadyBoundException {
		registry.bind(name, remoteObj);
		System.out.println("Registered: " + name + " -> "
				+ remoteObj.getClass().getName() + "[" + remoteObj + "]");
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			PORT = Integer.valueOf(args[0]);
		}
		startRegistry();
		registerObject(ControladorMando.class.getSimpleName(),
				new ControladorMandoImpl());
		Thread.sleep(5 * 60 * 1000);
	}
}
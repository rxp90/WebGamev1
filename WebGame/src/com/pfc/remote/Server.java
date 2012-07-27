package com.pfc.remote;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;


public class Server {
	public static final int PORT = 1099;
	private static Registry registry;

	public static void startRegistry() throws RemoteException {
		// create in server registry
		registry = java.rmi.registry.LocateRegistry.createRegistry(PORT);
	}

	public static void registerObject(String name, Remote remoteObj)
			throws RemoteException, AlreadyBoundException {
		registry.bind(name, remoteObj);
		System.out.println("Registered: " + name + " -> "
				+ remoteObj.getClass().getName() + "[" + remoteObj + "]");
	}

	public static void main(String[] args) throws Exception {
		startRegistry();
		registerObject(Api.class.getSimpleName(), new ApiImpl());
		Thread.sleep(5 * 60 * 1000);
	}
}
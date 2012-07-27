package com.pfc.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observer;

public interface Api extends Remote {

	public String apagarAcelerometro() throws RemoteException;

	public String encenderAcelerometro() throws RemoteException;

	public String leeLinea() throws RemoteException;

}

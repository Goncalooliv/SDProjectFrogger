package edu.ufp.inf.sd.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface gameFactoryRI extends Remote {
    public void register(String email, String password) throws RemoteException;

    public gameSessionRI login(String email, String password) throws RemoteException;
}

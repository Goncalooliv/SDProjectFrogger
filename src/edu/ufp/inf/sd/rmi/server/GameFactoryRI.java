package edu.ufp.inf.sd.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameFactoryRI extends Remote {
    void register(String email, String password) throws RemoteException;

    GameSessionRI login(String email, String password) throws RemoteException;
}

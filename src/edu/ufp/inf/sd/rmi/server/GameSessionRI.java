package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {

    void logout() throws RemoteException;

    Jogo createJogo(String dificuldade, ObserverRI observerRI) throws RemoteException;
}

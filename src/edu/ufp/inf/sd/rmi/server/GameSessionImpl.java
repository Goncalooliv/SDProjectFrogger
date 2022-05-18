package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    GameFactoryImpl gameFactoryImpl;
    String email;

    public GameSessionImpl(GameFactoryImpl gameFactoryImpl) throws RemoteException{
        super();
        this.gameFactoryImpl = gameFactoryImpl;
    }

    @Override
    public Jogo createJogo(String dificuldade, ObserverRI observerRI) throws RemoteException {
        System.out.println("linha 1");
        SubjectRI subjectRI = new SubjectImpl();
        System.out.println("linha 2");
        Jogo jogo = gameFactoryImpl.dbMockup.insert(dificuldade,subjectRI);
        System.out.println("linha 3");
        jogo.getSubjectRI().attach(observerRI);
        System.out.println("linha 4");
        return jogo;
    }

    @Override
    public Jogo joinJogo(int id, ObserverRI observerRI) throws RemoteException {
        return null;
    }

    /*@Override
    public ArrayList<Jogo> printFroggerGameList(){
        return this.gameFactoryImpl.dbMockup.jogos;
    }*/


    @Override
    public void logout() throws RemoteException {
        gameFactoryImpl.froggergame_sessions.remove(this);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOGOUT OH BORRRRRRRROOOOOOOOO");
    }
}

package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    GameFactoryImpl gameFactoryImpl;
    String email;

    public GameSessionImpl(GameFactoryImpl gameFactoryImpl, String email) throws RemoteException{
        super();
        this.gameFactoryImpl = gameFactoryImpl;
        this.email = email;
    }
    public GameSessionImpl(GameFactoryImpl gameFactoryImpl) throws RemoteException{
        super();
        this.gameFactoryImpl = gameFactoryImpl;
    }

    @Override
    public Jogo createJogo(String dificuldade, ObserverRI observerRI) throws RemoteException {
        SubjectRI subjectRI = new SubjectImpl();
        Jogo jogo = gameFactoryImpl.dbMockup.insert(dificuldade,subjectRI);
        jogo.getSubjectRI().attach(observerRI);
        return jogo;
    }


    @Override
    public void logout() throws RemoteException {
        gameFactoryImpl.froggergame_sessions.remove(this);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOGOUT OH BORRRRRRRROOOOOOOOO");
    }
}

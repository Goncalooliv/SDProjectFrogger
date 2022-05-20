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
    public Jogo createJogo(Integer playerNumber, String dificuldade, ObserverRI observerRI) throws RemoteException {
        SubjectRI subjectRI = new SubjectImpl();
        Jogo jogo = gameFactoryImpl.dbMockup.insert(playerNumber, dificuldade,subjectRI);
        jogo.getSubjectRI().attach(observerRI);
        return jogo;
    }

    @Override
    public Jogo joinJogo(int idJogo, ObserverRI observerRI) throws RemoteException {
        Jogo jogo = gameFactoryImpl.dbMockup.selectGame(idJogo);
        jogo.getSubjectRI().attach(observerRI);
        return jogo;
    }

    @Override
    public ArrayList<Jogo> printFroggerGameList() throws RemoteException{
        return this.gameFactoryImpl.dbMockup.printJogo();
    }



    @Override
    public void logout() throws RemoteException {
        gameFactoryImpl.froggergame_sessions.remove(this);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOGOUT OH BORRRRRRRROOOOOOOOO");
    }
}

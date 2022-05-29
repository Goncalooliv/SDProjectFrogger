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

    /**
     * Override do metodo de criar jogo
     * criamos um SubjectRI, inserimos o jogo na dbmockup e damos attach do observer ao jogo
     * @param playerNumber
     * @param dificuldade
     * @param observerRI
     * @return
     * @throws RemoteException
     */
    @Override
    public Jogo createJogo(Integer playerNumber, String dificuldade, ObserverRI observerRI) throws RemoteException {
        SubjectRI subjectRI = new SubjectImpl();
        Jogo jogo = gameFactoryImpl.dbMockup.insert(playerNumber, dificuldade,subjectRI);
        jogo.getSubjectRI().attach(observerRI);
        return jogo;
    }

    /**
     * Quando o user escolher um jogo para se conectar, dá attach ao observer e retorna o jogo
     * @param idJogo
     * @param observerRI
     * @return
     * @throws RemoteException
     */
    @Override
    public Jogo joinJogo(int idJogo, ObserverRI observerRI) throws RemoteException {
        Jogo jogo = gameFactoryImpl.dbMockup.selectGame(idJogo);
        jogo.getSubjectRI().attach(observerRI);
        return jogo;
    }

    /**
     * Imprime o arraylist de jogos para o terminal
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Jogo> printFroggerGameList() throws RemoteException{
        return this.gameFactoryImpl.dbMockup.printJogo();
    }


    /**
     * Metodo para o user dar logout
     * @throws RemoteException
     */
    @Override
    public void logout() throws RemoteException {
        gameFactoryImpl.froggergame_sessions.remove(this);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Thank you for playing");
    }
}

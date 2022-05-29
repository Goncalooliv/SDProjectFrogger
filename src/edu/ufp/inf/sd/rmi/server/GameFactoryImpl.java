package edu.ufp.inf.sd.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {
    public DBMockup dbMockup;
    HashMap<String, GameSessionImpl> froggergame_sessions = new HashMap<>();

    public GameFactoryImpl() throws RemoteException {
        super();
        this.dbMockup = new DBMockup();
    }

    /**
     * Metodo usado para dar register do User na DB
     * @param email
     * @param password
     * @throws RemoteException
     */
    @Override
    public void register(String email, String password) throws RemoteException {
        this.dbMockup.register(email, password);
    }

    /**
     * Metodo usado para o login do user
     * Vai à db ver se o email e pass introduzidos existem na DB
     * caso exista criam uma GameSession
     * @param email introduzido pelo user
     * @param password introduzido pelo user
     * @return
     * @throws RemoteException
     */
    @Override
    public GameSessionRI login(String email, String password) throws RemoteException {
        if (dbMockup.exists(email, password)) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Login efetuado com sucesso!");
            GameSessionImpl session = new GameSessionImpl(this);
            froggergame_sessions.put(email, session);
            return session;
        }
        return null;
    }

    public void destroySession(String s) throws RemoteException{
        this.froggergame_sessions.remove(s);
    }

    public DBMockup getDbMockup(){return dbMockup;}
}

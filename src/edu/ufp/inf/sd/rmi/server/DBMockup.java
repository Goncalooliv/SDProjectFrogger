package edu.ufp.inf.sd.rmi.server;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBMockup {

    public ArrayList<User> users;
    public ArrayList<Jogo> jogos;

    public DBMockup() {
        this.users = new ArrayList<>();
        this.jogos = new ArrayList<>();
    }

    /**
     * Registers a new user.
     *  @param e username
     * @param p password
     * @return
     */
    public void register(String e, String p){
        if (!exists(e, p)) {
            users.add(new User(e, p));
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User Registered with Success");
        }else{
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User already exists!");
        }

    }

    /**
     * Checks the credentials of an user.
     *
     * @param e email
     * @param p password
     * @return
     */
    public boolean exists(String e, String p){
        for(User user : this.users){
            if(user.getEmail().compareTo(e) == 0 && user.getPassword().compareTo(p) == 0){
                return true;
            }
        }
        return false;
    }

    public User getUser(String email) {
        for(User u : this.users) {
            if(u.getEmail().compareTo(email) == 0) {
                return u;
            }
        }
        return null;
    }

    public Jogo insert(String dificuldade, SubjectRI subjectRI) {
        Jogo jogo = new Jogo(dificuldade,subjectRI);
        jogos.add(jogo);
        return jogo;
    }



}

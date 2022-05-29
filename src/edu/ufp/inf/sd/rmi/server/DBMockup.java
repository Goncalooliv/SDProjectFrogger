package edu.ufp.inf.sd.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
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

    /**
     * Imprime os jogos disponiveis
     * @return
     */
    public ArrayList<Jogo> printJogo(){
        for(Jogo jogo : jogos){
            System.out.println("ID do jogo: " + jogo.id);
        }
        return jogos;
    }

    public User getUser(String email) {
        for(User u : this.users) {
            if(u.getEmail().compareTo(email) == 0) {
                return u;
            }
        }
        return null;
    }

    /**
     * Insere o jogo criado no array de jogos
     * @param playerNumber numero de jogadores que aquele jogo pretende ter
     * @param dificuldade que o jogo terá
     * @param subjectRI
     * @return
     */
    public Jogo insert(Integer playerNumber, String dificuldade, SubjectRI subjectRI) {
        Jogo jogo = new Jogo(playerNumber, dificuldade,subjectRI);
        jogos.add(jogo);
        return jogo;
    }

    /*public Jogo[] select(int numeroPlayers) {
        Jogo[] jogos1;
        ArrayList<Jogo> jogos2 = new ArrayList<>();
        for (int i = 0; i < jogos.size(); i++) {
            Jogo jogo = jogos.get(i);
            System.out.println("DB - select(): Jogo[" + i + "] = Jogo com " + jogo.getPlayerNumber() + " jogadores");
            if (jogo.getPlayerNumber() == numeroPlayers) {
                jogos2.add(jogo);
            }
        }
        jogos1 = new Jogo[jogos2.size()];
        for (int i = 0; i < jogos2.size(); i++) {
            jogos1[i] = jogos2.get(i);
        }
        return jogos1;
    }*/

    /**
     * Percorre o array de jogos e se encontrar o id introduzido imprime a info desse jogo
     * @param idJogo
     * @return
     */
    public Jogo selectGame(int idJogo){
        for(Jogo jogo : jogos){
            if(jogo.getId() == idJogo){
                System.out.println("Numero de Players" + jogo.getPlayerNumber() +  "Dificuldade" + jogo.getDificuldade());
                return jogo;
            }
        }
        return null;
    }







}

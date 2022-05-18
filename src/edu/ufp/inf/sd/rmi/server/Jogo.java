package edu.ufp.inf.sd.rmi.server;

import java.io.Serializable;

public class Jogo implements Serializable {
    public int id;
    private String dificuldade;
    private int playerNumber;
    private SubjectRI SubjectRI;

    public Jogo(String dificuldade, SubjectRI subjectRI) {
        this.dificuldade = dificuldade;
        this.playerNumber = 0;
        SubjectRI = subjectRI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerNumber(){
        return this.playerNumber;
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public SubjectRI getSubjectRI() {
        return SubjectRI;
    }

    public void setSubjectRI(SubjectRI subjectRI) {
        SubjectRI = subjectRI;
    }
}

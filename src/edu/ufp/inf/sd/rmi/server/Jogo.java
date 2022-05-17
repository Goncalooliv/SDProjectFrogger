package edu.ufp.inf.sd.rmi.server;

import java.io.Serializable;

public class Jogo implements Serializable {
    private int id;
    private String dificuldade;
    private SubjectRI SubjectRI;

    public Jogo(String dificuldade, SubjectRI subjectRI) {
        id++;
        this.dificuldade = dificuldade;
        SubjectRI = subjectRI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

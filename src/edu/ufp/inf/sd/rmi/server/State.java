package edu.ufp.inf.sd.rmi.server;

import java.io.Serializable;


public class State implements Serializable {
    private String msg;
    private int id;

    /**
     *
     * @param id
     * @param m
     */
    public State(int id, String m) {
        this.id = id;
        this.msg = m;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getInfo(){
        return this.msg;
    }

    /**
     *
     * @param m
     */
    public void setInfo(String m){
        this.msg = m;
    }
}

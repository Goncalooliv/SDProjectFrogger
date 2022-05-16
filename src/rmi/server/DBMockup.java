package rmi.server;

import java.util.ArrayList;

public class DBMockup {

    private final ArrayList<User> users;

    public DBMockup() {
        users = new ArrayList<>();
    }

    /**
     * Registers a new user.
     *
     * @param e username
     * @param p password
     */
    public void register(String e, String p){
        if(!exists(e,p)){
            users.add(new User(e,p));
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
}

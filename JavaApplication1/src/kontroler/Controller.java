/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import db.DBBroker;
import domen.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matej
 */
public class Controller {

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    /////////// user ////////
    public User login(String username, String password) {
        DBBroker.getInstance().connect();

        List<User> lista = DBBroker.getInstance().getAllUsers();

        for (User user : lista) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        DBBroker.getInstance().disconnect();

        return null;
    }

    public List<User> getAllUsers() {
        DBBroker.getInstance().connect();

        List<User> users = DBBroker.getInstance().getAllUsers();

        DBBroker.getInstance().disconnect();

        return users;
    }

    public boolean addUser(User newUser) {
        boolean uspesno = false;
        DBBroker.getInstance().connect();

        try {
            DBBroker.getInstance().addUser(newUser);
            DBBroker.getInstance().commit();

            uspesno = true;
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

            DBBroker.getInstance().rollback();
            uspesno = false;
        }

        DBBroker.getInstance().disconnect();

        return uspesno;
    }

    public boolean updateUser(User user) {
        boolean uspesno = false;
        DBBroker.getInstance().connect();

        try {
            DBBroker.getInstance().updateUser(user);
            DBBroker.getInstance().commit();
            uspesno = true;
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            DBBroker.getInstance().rollback();
            uspesno = false;
        }

        DBBroker.getInstance().disconnect();

        return uspesno;
    }

    public boolean deleteUser(User user) {
        boolean uspesno = false;
        DBBroker.getInstance().connect();

        try {
            DBBroker.getInstance().deleteUser(user);

            DBBroker.getInstance().commit();
            uspesno = true;
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            DBBroker.getInstance().rollback();
            uspesno = false;
        }

        DBBroker.getInstance().disconnect();

        return uspesno;

    }

    /////////// user role ////////
    public List<String> getUserRoles(User user){
        DBBroker.getInstance().connect();
               
        List<String> lista = DBBroker.getInstance().getUserRoles(user);
        
        DBBroker.getInstance().disconnect();
        
        return lista;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import db.DBBroker;
import domen.User;
import java.util.List;
import server.PokretanjeServera;
import views.ServerskaForma;

/**
 *
 * @author drago
 */
public class Kontroler {

    private static Kontroler instance;
    PokretanjeServera server;

    private Kontroler() {
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            System.out.println("napravljen kontroler");
            instance = new Kontroler();
        }
        return instance;
    }
    
    public void pokreniServer(ServerskaForma sf) {
        if (server == null || server.isKraj()){
            server = new PokretanjeServera(sf);
            server.start();
        }
    }
    
    public void zaustaviServer(){
        if (!server.isKraj()){
            server.zaustaviServer();
        }
    }

   
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
}

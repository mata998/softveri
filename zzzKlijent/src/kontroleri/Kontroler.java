/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroleri;

import domen.User;
import komunikacija.KomunikacijaSaServerom;
import komunikacija.ObradaServerskihOdgovora;
import konstane.Operacije;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import views.GlavnaForma;
import views.LoginForma;

/**
 *
 * @author matej
 */
public class Kontroler {
    private static Kontroler instance;
    ObradaServerskihOdgovora obradaOdgovora;
    LoginForma loginForma;
    GlavnaForma glavnaForma;
    
    User user;
    
    private Kontroler() {
        KomunikacijaSaServerom.getInstance();
        obradaOdgovora = new ObradaServerskihOdgovora();
        obradaOdgovora.start();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            System.out.println("napravljen kontroler");
            instance = new Kontroler();
        }
        return instance;
    }
    
    public void otvoriLoginFormu(){
        
        loginForma = new LoginForma();
        loginForma.setLocationRelativeTo(null);
        loginForma.setVisible(true);
        
        
    }

    public void otvoriGlavnuFormu(){
        
        glavnaForma = new GlavnaForma();
        glavnaForma.setLocationRelativeTo(null);
        glavnaForma.setVisible(true);
        
    }

//////////////////// server komunikacija ////////

    public void posaljiLogin(String username, String password){
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.LOGIN);
        kz.setUsername(username);
        kz.setPassword(password);

        KomunikacijaSaServerom.getInstance().posaljiZahtev(kz);
        
        System.out.println("login poslat");
    }
    

    public void primiLogin(ServerskiOdgovor so) {
         if (so.isSuccessfull()){

            System.out.println("IDEMO BRE LOGIN");
            System.out.println(so.getUser().getUsername());
            user = so.getUser();
            
            
            otvoriGlavnuFormu();
            loginForma.dispose();
        }
        else {
            System.out.println("kurac");
        
         }
    }
    
}

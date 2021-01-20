/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjukontroler;

import domen.User;
import forme.GlavnaForma;
import forme.IzmeniUseraForma;
import forme.LoginForma;
import forme.PrikaziUsereForm;
import forme.RoleForma;
import forme.UnesiUseraForma;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author matej
 */
public class ViewController {

    private static ViewController instance;
    private LoginForma loginForm;
    private GlavnaForma glavnaForma;
    private PrikaziUsereForm useriForma;
    private UnesiUseraForma unesiUseraForma;
    private IzmeniUseraForma izmeniUseraForma;
    private RoleForma roleForma;
    private User user;

    private ViewController() {
    }

    public static ViewController getInstance() {
        if (instance == null) {
            instance = new ViewController();
        }

        return instance;
    }

    ///// open ///////
    public void openGlavnuFormu() {

        glavnaForma = new GlavnaForma();
        glavnaForma.setLocationRelativeTo(null);
        glavnaForma.setVisible(true);
    }

    public void openLogIn() {

        loginForm = new LoginForma();
        loginForm.setLocationRelativeTo(null);
        loginForm.setVisible(true);
    }

    public void openPrikaziUsere(JFrame roditelj) {

        useriForma = new PrikaziUsereForm(roditelj, true);
        useriForma.setLocationRelativeTo(null);
        useriForma.setVisible(true);
    }

    public void openUnesiUsera(JFrame roditelj) {

        unesiUseraForma = new UnesiUseraForma(roditelj, true);
        unesiUseraForma.setLocationRelativeTo(null);
        unesiUseraForma.setVisible(true);

    }

    public void openIzmeniUsera(JFrame roditelj, PrikaziUsereForm praviRoditelj, User user) {

        izmeniUseraForma = new IzmeniUseraForma(roditelj, true, praviRoditelj, user);
        izmeniUseraForma.setLocationRelativeTo(null);
        izmeniUseraForma.setVisible(true);

    }
    
    public void openRole(JFrame roditelj) {

        roleForma = new RoleForma(roditelj, true);
        roleForma.setLocationRelativeTo(null);
        roleForma.setVisible(true);

    }

/////////////////////////

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

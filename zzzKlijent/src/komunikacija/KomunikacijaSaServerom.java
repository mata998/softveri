/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author drago
 */
public class KomunikacijaSaServerom {

    private static KomunikacijaSaServerom instance;
    Socket socket;

    private KomunikacijaSaServerom() {
        try {
            socket = new Socket("localhost", 9000);
        } catch (IOException ex) {
            Logger.getLogger(KomunikacijaSaServerom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static KomunikacijaSaServerom getInstance() {
        if (instance == null) {
            instance = new KomunikacijaSaServerom();
        }

        return instance;
    }

    public void posaljiZahtev(KlijentskiZahtev kz) {

        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(kz);
//            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(KomunikacijaSaServerom.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ServerskiOdgovor primiOdgovor() {
        ObjectInputStream ois = null;
        ServerskiOdgovor so = new ServerskiOdgovor();

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            so = (ServerskiOdgovor) ois.readObject();
            
        } catch (IOException ex) {
            Logger.getLogger(KomunikacijaSaServerom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KomunikacijaSaServerom.class.getName()).log(Level.SEVERE, null, ex);
        }

        return so;
    }
}

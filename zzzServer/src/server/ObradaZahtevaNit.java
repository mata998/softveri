/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstane.Operacije;
import kontroleri.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import views.ServerskaForma;

/**
 *
 * @author drago
 */
public class ObradaZahtevaNit extends Thread {

    Socket socket;
    ServerskaForma sf;
    boolean kraj = false;

    public ObradaZahtevaNit(Socket socket, ServerskaForma sf) {
        this.socket = socket;
        this.sf = sf;
    }

    @Override
    public void run() {
        try {

            while (!kraj) {
                KlijentskiZahtev kz;
                System.out.println("cekam zahtev");
                kz = primiZahtev();
                ServerskiOdgovor so = new ServerskiOdgovor();
                switch (kz.getOperacija()) {
                    case Operacije.LOGIN:
                        System.out.println("STIGLO");

                        User user = Kontroler.getInstance().login(kz.getUsername(), kz.getPassword());


                        if (user != null){
                            so.setOperacija(Operacije.LOGIN);
                            so.setSuccessfull(true);
                            so.setUser(user);
                        }
                        else {
                            so.setOperacija(Operacije.LOGIN);
                            so.setSuccessfull(false);
                        }

                        break;
                }

                posaljiOdgovor(so);
            }
                  
        } 
        catch (Exception ex) {
//                Logger.getLogger(ObradaZahtevaNit.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("klijent se izlogovao");
            }
    }

    private KlijentskiZahtev primiZahtev() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        KlijentskiZahtev kz = new KlijentskiZahtev();
        
        kz = (KlijentskiZahtev) ois.readObject();
        
        return kz;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
    ObjectOutputStream oos = null;

        try {
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(so);
            
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahtevaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

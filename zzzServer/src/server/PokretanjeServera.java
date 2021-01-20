/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.ServerskaForma;

/**
 *
 * @author drago
 */
public class PokretanjeServera extends Thread {

    private ServerskaForma sf;
    private ServerSocket ss;
    private boolean kraj;

    public PokretanjeServera(ServerskaForma sf) {
        this.sf = sf;
        this.kraj = false;
    }

    @Override
    public void run() {
        try {            
            kraj = false;
            ss = new ServerSocket(9000);
            System.out.println("Server pokrenut");
            
            while (!kraj){
                Socket s = ss.accept();
                System.out.println("Klijent se povezao.");
                ObradaZahtevaNit oz = new ObradaZahtevaNit(s, sf);
                oz.start();
            }
            
        } catch (IOException ex) {
//            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server zaustavljen");
        }
    }

    public void zaustaviServer() {
        try {
            kraj = true;
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean isKraj() {
        return kraj;
    }

   

    
}

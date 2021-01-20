/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import konstane.Operacije;
import kontroleri.Kontroler;
import transfer.ServerskiOdgovor;

/**
 *
 * @author matej
 */
public class ObradaServerskihOdgovora extends Thread{
    boolean kraj = false;

    @Override
    public void run() {
        while (!kraj){
            System.out.println("cekam odgovor");
            ServerskiOdgovor so = KomunikacijaSaServerom.getInstance().primiOdgovor();
            
            switch (so.getOperacija()){
                
                case Operacije.LOGIN: 
                    
                    Kontroler.getInstance().primiLogin(so);
                    
                    break;
                
            }
        }

    }
    
    
}

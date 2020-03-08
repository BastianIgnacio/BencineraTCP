/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surtidorr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sucursal.Informacion;
import sucursal.Transaccion;

/**
 *
 * @author moris
 */
public class Cliente implements Runnable{
    Transaccion trans;
    public void setTransaccion(Transaccion trans){
        this.trans = trans;
    }
    
    @Override
    public void run() {
        final String HOST = "127.0.0.1";
        final int PUERTO = 5000;
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            Socket sc = new Socket(HOST,PUERTO);
            
            //Flujo para recibir objetos
            out = new ObjectOutputStream(sc.getOutputStream());
            // Se escribe un objeto Informacion.
            out.writeObject(this.trans);
            
            
            in = new ObjectInputStream(sc.getInputStream());
            Object obj = in.readObject();
            if(obj instanceof Informacion){
                Informacion info = (Informacion)obj;
                System.out.println(info);
            }else if(obj instanceof Transaccion){
                Transaccion trans = (Transaccion)obj;
                System.out.println(trans);
            }
                        
            sc.close();
            System.out.println("Cliente cerrado");
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

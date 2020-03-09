/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

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
    Informacion info;
    String comando;
    public void setInformacion(Informacion info){
        this.info = info;
    }
    public void setComando(String cmd){
        this.comando = cmd;
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
            if(this.info != null)
                out.writeObject(this.info);
            else
                out.writeObject(this.comando);
            out.flush();
            
            in = new ObjectInputStream(sc.getInputStream());
            Object obj = in.readObject();
                if(obj instanceof Informacion){
                    Informacion info = (Informacion)obj;
                    this.info = info;
                    SharedInfo.info = info;
                    System.out.println(info);
                }
            // Se escribe un objeto Informacion.
            out.writeObject(this.info);
            
            
            in = new ObjectInputStream(sc.getInputStream());
                        
            sc.close();
            System.out.println("Cliente cerrado");
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

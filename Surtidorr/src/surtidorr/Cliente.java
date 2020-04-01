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
    String comando;
    public void setTransaccion(Transaccion trans){
        this.trans = trans;
    }
    
    public void setComando(String cmd){
        this.comando = cmd;
    }
    
    @Override
    public void run() {
        final String HOST = SharedInfo.ip;
        final int PUERTO = 5000;
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            Socket sc = new Socket(HOST,PUERTO);
            
            //Flujo para recibir objetos
            out = new ObjectOutputStream(sc.getOutputStream());
            // Se escribe un objeto Informacion.
            if(this.trans != null)
                out.writeObject(this.trans);
            else
                out.writeObject(this.comando);
            out.flush();
            
            in = new ObjectInputStream(sc.getInputStream());
                Object obj = in.readObject();
                if(obj instanceof Informacion){
                    Informacion info = (Informacion)obj;
                    SharedInfo.info = info;
                    System.out.println(info);
                }else if(obj instanceof Transaccion){
                    Transaccion trans = (Transaccion)obj;
                    System.out.println(trans);
                    System.out.println((String)in.readObject());
                }
            sc.close();
            System.out.println("Cliente cerrado");
        } catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {
            
        }
    }
    
}

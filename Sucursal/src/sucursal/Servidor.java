package sucursal;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import sucursal.FXMLDocumentController;
import sucursal.Transaccion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author moris
 */
public class Servidor implements Runnable {

    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket sc = null;
        final int PUERTO = 5000;
        
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            while(true)
            {
                System.out.println("Esperando..");
                sc = servidor.accept();
                out= new ObjectOutputStream(sc.getOutputStream());
                System.out.println("Creando la trans");
                Transaccion t = crear();
                out.writeObject(t);
                
                //in = new ObjectInputStream(sc.getInputStream());
                
                
                
                System.out.println("socket Cerrado");
            } 
            
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            try {
                sc.close();
            } catch (IOException ex1) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    private Transaccion crear(){
        Timestamp time = null;
        String idTransaccion = "11";
        String tipoCombustible = "diesel";
        int litros = 15;
        int precioPorLitro= 500;
        int total=15000;
        
        Transaccion t = new Transaccion(time,idTransaccion,tipoCombustible,litros,precioPorLitro,total);
        return t;
    }
    
}

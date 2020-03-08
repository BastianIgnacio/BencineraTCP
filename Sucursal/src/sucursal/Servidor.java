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

    Informacion info;
    BaseDeDatos bd;
    public Servidor(){
        this.bd = BaseDeDatos.crearInstancia();
        this.info = new Informacion(0,0,0,0,0);
    }
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
                if(sc.isConnected()){
                    System.out.println("Cliente conectado");
                    
                }
                out = new ObjectOutputStream(sc.getOutputStream());
                in = new ObjectInputStream(sc.getInputStream());
                
                    Object obj = in.readObject();
                    System.out.println("Objeto leido " + obj.getClass().toString());
                    if(obj instanceof Informacion){
                        Informacion info = (Informacion)obj;
                        this.info = info;
                        System.out.println(info);
                    }else if(obj instanceof Transaccion){
                        Transaccion trans = (Transaccion)obj;
                        out.writeObject(this.info);
                        System.out.println(trans);
                    }else if(obj instanceof String){
                        String str = (String)obj;
                        System.out.println("Comando recibido: " + str);
                        switch(str){
                            case "actualizar_precios":
                                out.writeObject(this.info);
                                break;
                        }
                    }
                    /*
                    int surtidor = trans.getRefSurtidor();
                    if(!this.bd.checkSurtidor(surtidor)){
                        this.bd.insertSurtidor(0, 0, 0, 0, 0);
                        System.out.println("INFO: Insertando surtidor no existente.");
                    }*/
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
        
        Transaccion t = new Transaccion(time,idTransaccion,tipoCombustible,litros,precioPorLitro,total, 1);
        return t;
    }

    
}

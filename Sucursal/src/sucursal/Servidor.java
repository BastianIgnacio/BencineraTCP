package sucursal;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    BaseDeDatos bd;

     ArrayList<Worker> surtidores;
    
    FXMLDocumentController controlador; 
    
    
    
    public Servidor(){
        this.surtidores = new ArrayList<>();
        this.bd = BaseDeDatos.crearInstancia();
    }
    
     public void checkSucursales(){
        for (Worker suc : this.surtidores) {
            suc.check();
            if(suc.isConnected()){
                System.out.println("Sucursal " + suc.getAddress() + " en linea.");
            }else{
                System.out.println("Sucursal " + suc.getAddress() + " sin conexion.");
            }
        }
    }
     
     public void checkSucursal(Worker newSuc){
        ArrayList<Worker> copia = new ArrayList<>(this.surtidores);
        for (Worker suc : copia) {
            if(suc.getAddress().equals(newSuc.getAddress())){
                this.surtidores.remove(suc);
            }
        }
    }
    
    
    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket sc = null;
        final int PUERTO = 6000;
        
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            
            while(true){
                System.out.println("Esperando..");
                sc = servidor.accept();
                Worker w = new Worker(sc,bd,controlador);
                
                this.checkSucursal(w);
                this.surtidores.add(w);
                new Thread(w).start();
                
            }
            
        } catch (Exception ex) {
            try {
                sc.close();
            } catch (IOException ex1) {
                System.out.println("ERROR: No se puede cerrar el servidor de socket.");
            }
            System.out.println("ERROR: Error durante la ejecucion del servidor.");
        }
        
        
    }
    
    
    public FXMLDocumentController getControlador() {
        return controlador;
    }

    public void setControlador(FXMLDocumentController controlador) {
        this.controlador = controlador;
    }
    

    
}

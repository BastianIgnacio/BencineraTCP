/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moris
 */
public class Servidor implements Runnable {

    ArrayList<Worker> sucursales;
    
    public Servidor(){
        this.sucursales = new ArrayList<>();
    }
    
    public void checkSucursales(){
        for (Worker suc : this.sucursales) {
            suc.check();
            if(suc.isConnected()){
                System.out.println("Sucursal " + suc.getAddress() + " en linea.");
            }else{
                System.out.println("Sucursal " + suc.getAddress() + " sin conexion.");
            }
        }
    }
    
    /**
     * Metodo para chequear si es que ya existe en la lista de conectados, si existe se elimina.
     */
    public void checkSucursal(Worker newSuc){
        ArrayList<Worker> copia = new ArrayList<>(this.sucursales);
        for (Worker suc : copia) {
            if(suc.getAddress().equals(newSuc.getAddress())){
                this.sucursales.remove(suc);
            }
        }
    }
    
    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket sc = null;
        final int PUERTO = 5000;
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            
            while(true){
                System.out.println("Esperando..");
                sc = servidor.accept();
                Worker w = new Worker(sc);
                
                this.checkSucursal(w);
                this.sucursales.add(w);
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
}

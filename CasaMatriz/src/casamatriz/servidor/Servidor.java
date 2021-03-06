/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz.servidor;

import casamatriz.FXMLDocumentController;
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
    ServerSocket servidor = null;
    FXMLDocumentController controller;
    public Servidor(FXMLDocumentController controller){
        this.sucursales = new ArrayList<>();
        this.controller = controller;
    }

    public ArrayList<Worker> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Worker> sucursales) {
        this.sucursales = sucursales;
    }

    public void cerrar(){
        try {
            for (Worker suc : this.sucursales) {
                suc.cerrar();
            }
            this.servidor.close();
        } catch (IOException ex) {
            System.out.println("ERROR: No se puede cerrar el servidor.");
        }
    }
    
    public void updateSucursales(){
        try{
            this.controller.updateSucursales(sucursales);
        }catch(Exception ex){}
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
        
        Socket sc = null;
        final int PUERTO = 5000;
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            
            while(true){
                System.out.println("Esperando..");
                sc = servidor.accept();
                Worker w = new Worker(sc, this);
                
                this.checkSucursal(w);
                this.sucursales.add(w);
                this.controller.updateSucursales(sucursales);
                new Thread(w).start();
                
            }
            
        } catch (IOException ex) {
            try {
                sc.close();
            } catch (IOException ex1) {
                System.out.println("ERROR: No se puede cerrar el servidor de socket.");
            } catch (NullPointerException nex){
                System.out.println("ERROR: Hilo servidor terminado forzosamente.");
            }
        }
    }
}

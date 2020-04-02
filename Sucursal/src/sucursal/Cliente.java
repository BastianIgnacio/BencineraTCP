/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roduc
 */
public class Cliente implements Runnable{
    String ipPrimaria = InfoSurtidor.ipPrimaria;
    String ipSecundaria = InfoSurtidor.ipSecundaria;
    String nombre = InfoSurtidor.nombreSucursal;
    FXMLDocumentController controlador;
    
    int contador = 1;
    BaseDeDatos bd;
   
    public void setControler(FXMLDocumentController cont){
        this.controlador = cont;
    }

    public Cliente() {
        this.bd = BaseDeDatos.crearInstancia();
    }
    
     @Override
    public void run() {
        this.crearConexion(ipPrimaria);
    }

    public void crearConexion(String ip) {
        Socket s = null;
        try {
            s = new Socket(ip,5000);
            this.isConnected(true);
            ObjectOutputStream dos = null;
            ObjectInputStream din = null;
            dos = new ObjectOutputStream(s.getOutputStream());
            dos.writeObject("Nombre:"+ nombre);
            dos.flush();
            
            while (true){
                if(1==2){
                    break;
                }
                din = new ObjectInputStream(s.getInputStream());
                Object obj = din.readObject();
                

                if (obj instanceof Informacion) {
                    Informacion info = (Informacion) obj;
                    InfoSurtidor.info = info;
                    System.out.println(info);
                }
                else if (obj instanceof String) {
                    String str = (String) obj;
                    System.out.println("Mensaje: " + (String) obj);
                    switch (str) {
                        case "actualizar_precios":
                            dos.writeObject(InfoSurtidor.info);
                            dos.flush();
                            break;
                    }
                }
            }
            // closing resources 
            dos.close();
            din.close();
        } catch (ConnectException cntex) {
            this.isConnected(false);
            try {
                Thread.sleep(5000);
                InfoSurtidor.intentoConexion++;
                if(InfoSurtidor.intentoConexion==11){
                    InfoSurtidor.intentoConexion=0;
                }
                if(InfoSurtidor.intentoConexion<=5){
                    System.out.println("ERROR: No se pudo conectar a la casa matriz, intentando conectar a casa matriz primaria.");
                    crearConexion(ipPrimaria);
                }else if(InfoSurtidor.intentoConexion>5 && InfoSurtidor.intentoConexion<=10){
                    System.out.println("ERROR: No se pudo conectar a la casa matriz, intentando conectar a casa matriz secundaria.");
                    crearConexion(ipSecundaria);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ioex) {
            this.isConnected(false);
            try {
                Thread.sleep(5000);
                InfoSurtidor.intentoConexion++;
                if(InfoSurtidor.intentoConexion==11){
                    InfoSurtidor.intentoConexion=0;
                }
                if(InfoSurtidor.intentoConexion<=5){
                    System.out.println("ERROR: No se pudo conectar a la casa matriz, intentando conectar a casa matriz primaria.");
                    crearConexion(ipPrimaria);
                }else if(InfoSurtidor.intentoConexion>5 && InfoSurtidor.intentoConexion<=10){
                    System.out.println("ERROR: No se pudo conectar a la casa matriz, intentando conectar a casa matriz secundaria.");
                    crearConexion(ipSecundaria);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public void isConnected(boolean con) {
       boolean conectado = con;
       this.controlador.comprobarConexion(conectado);
    }
    
   
   
    
    

}

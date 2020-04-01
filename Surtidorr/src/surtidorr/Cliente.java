/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surtidorr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sucursal.Informacion;
import sucursal.Transaccion;

/**
 *
 * @author roduc
 */
public class Cliente implements Runnable {

    Transaccion trans;
    String comando = null;
    ArrayList<Transaccion> pendientes;
    Socket s;
    boolean conexion;
    FXMLDocumentController controlador; 
    ObjectInputStream din;
    ObjectOutputStream dos;
    boolean primer = true;
    
    public void setTransaccion(Transaccion trans) {
        this.trans = trans;
    }

    public void setComando(String cmd) {
        this.comando = cmd;
    }
    
    public void setControler(FXMLDocumentController cont){
        this.controlador = cont;
    }

    @Override
    public void run() {
        this.crearConexion();
    }

    public void crearConexion()  {
        this.s = null;
        try {
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056 
            s = new Socket("25.64.202.245", 5000);
            //this.isConnected();
            
            System.out.println("INFO: Conectado a sucursal");
            
            
            while (true) {
                if(1==2){
                    break;
                }
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                    //if(in.available()>0) {
                        Object obj = in.readObject();
                        System.out.println("Objeto leido " + obj.getClass().toString());

                    if (obj instanceof Informacion) {
                        Informacion info = (Informacion) obj;
                        SharedInfo.info = info;
                        System.out.println(info);
                    } else if (obj instanceof Transaccion) {
                        Transaccion trans = (Transaccion) obj;
                        System.out.println(trans);
                        System.out.println((String) din.readObject());
                    }
                    if (obj instanceof String) {
                        String str = (String) obj;
                        if(str.equals("OK")){
                            dos = new ObjectOutputStream(s.getOutputStream());
                            dos.writeObject("actualizar_precios");
                            dos.flush();
                        }
                        System.out.println("Mensaje: " + (String) obj);

                    }
                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject("OK");
                        out.flush();
            }
            // closing resources 
            dos.close();
            din.close();
        } catch (ConnectException cntex) {
            //this.isConnected();
            System.out.println("Error en la conexion con la sucursal, reintentando en 5s");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        }catch (IOException ioex) {
           // this.isConnected();
            ioex.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        }
        //this.isConnected();
           catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void isConnected() {
       boolean conectado = !this.s.isClosed();
       this.controlador.comprobarConexion(conectado);
    }
    
    public void enviarTransaccion(Transaccion t) throws IOException{
        try {
            if(!this.s.isClosed()){
               dos = new ObjectOutputStream(s.getOutputStream());
               System.out.println("enviar");
               dos.writeObject(t);
               dos.flush();
            }
        } catch (IOException ex) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: No se pudo enviar la transaccion");
        }
    }

}

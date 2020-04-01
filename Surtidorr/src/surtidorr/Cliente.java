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
            Scanner scn = new Scanner(System.in);

            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056 
            s = new Socket("25.64.202.245", 5000);
            //this.isConnected();
            
            

            ObjectOutputStream dos = null;
            ObjectInputStream din = null;
            dos = new ObjectOutputStream(s.getOutputStream());
            if (this.comando != null) {
                dos.writeObject(this.comando);
                dos.flush();
            }
               
            while (true) {
                //Object obj = dis.readObject();
//              System.out.println((String)obj);
                
                //System.out.print("Enviar respuesta:");
                //String tosend = scn.nextLine();
                String tosend = "conexion abierta";
                //dos.writeObject(tosend);
                //dos.flush();
                

                if (tosend.equals("Exit")) {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

               

                din = new ObjectInputStream(s.getInputStream());
                Object obj = din.readObject();

                if (obj instanceof String) {
                    String str = (String) obj;
                    System.out.println("Mensaje: " + (String) obj);

                }

                if (obj instanceof Informacion) {
                    Informacion info = (Informacion) obj;
                    SharedInfo.info = info;
                    System.out.println(info);
                } else if (obj instanceof Transaccion) {
                    Transaccion trans = (Transaccion) obj;
                    System.out.println(trans);
                    System.out.println((String) din.readObject());
                }

            }

            // closing resources 
            scn.close();
            dos.close();
            din.close();
        } catch (ConnectException cntex) {
            //this.isConnected();
            System.out.println("Error en la coneccion con la sucursal, reintentando en 5s");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        } catch (IOException ioex) {
           // this.isConnected();
            System.out.println("Excepcion en flujos");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void isConnected() {
       boolean conectado = !this.s.isClosed();
       this.controlador.comprobarConexion(conectado);
    }
    
    public void enviarTransaccion(Transaccion t) throws IOException{
       
        ObjectOutputStream out = null;
        try {
             System.out.println("enviar");
            out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(t);
            out.close();
        } catch (IOException ex) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

}

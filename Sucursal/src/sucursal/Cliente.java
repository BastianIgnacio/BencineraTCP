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
public class Cliente {
    BaseDeDatos bd;
   

    public Cliente() {
        this.bd = BaseDeDatos.crearInstancia();
    }
    
    

    public void crearConexion() {
        Socket s = null;
        try {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056 
            s = new Socket("25.18.101.131", 5000);

            ObjectOutputStream dos = null;
            ObjectInputStream din = null;
            // obtaining input and out streams 
            //ObjectInputStream dis = new ObjectInputStream(s.getInputStream()); 

            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true) {
                //              Object obj = dis.readObject();
//                System.out.println((String)obj);
                dos = new ObjectOutputStream(s.getOutputStream());
                //System.out.print("Enviar respuesta:");
                //String tosend = scn.nextLine();
                String tosend ="conexion abierta";
                //dos.writeObject(tosend);
                //dos.flush();

                // If client sends exit,close this connection  
                // and then break from the while loop 
                if (tosend.equals("Exit")) {
                    System.out.println("Closing this connection : " + s);
                    
                    System.out.println("Connection closed");
                    break;
                }

                din = new ObjectInputStream(s.getInputStream());
                Object obj = din.readObject();
                

                if (obj instanceof Informacion) {
                    Informacion info = (Informacion) obj;
                    InfoSurtidor.info = info;
                    this.bd.actualizarPrecios(info);
                    this.bd.getPrecios();
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
                // printing date or time as requested by client
                // 
            }

            // closing resources 
            scn.close();
            dos.close();
            din.close();
        } catch (ConnectException cntex) {
            System.out.println("No se pudo conectara l servidor, reintentando en 5s");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        } catch (IOException ioex) {
            System.out.println("Se corto la comunicacion, reintentando en 5s");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            crearConexion();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
    
    

}

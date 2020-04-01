package casamatriz.servidor;

import casamatriz.SharedInfo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sucursal.Informacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author moris
 */
public class Worker extends Thread {
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    String address;
    final Socket s; 
    String nombre;
    boolean conectado;

    
  
    // Constructor 
    public Worker(Socket s) { 
        this.s = s; 
        this.address = s.getInetAddress().getHostAddress();
    } 
    
    public boolean isConnected(){
        return !this.s.isClosed();
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    public void enviar(){
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(SharedInfo.info);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void check(){
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject("OK");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
  
    @Override
    public void run() {
        String received = "",
               toreturn = "";
        if(this.s.isConnected()){
            System.out.println("[" + this.s.getInetAddress().getHostAddress() + "] Sucursal conectada.");
        }
        while(true){
            try{
                if(!this.s.isClosed()){
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream()); 
                    Object obj = in.readObject();
                    System.out.println("Objeto leido " + obj.getClass().toString());
                    
                    if(obj instanceof String){
                        System.out.println("Mensaje: " + (String)obj);
                    }
                    if(obj instanceof Informacion){
                        Informacion info = (Informacion)obj;
                        System.out.println(info);
                    }
                    
                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject("OK");
                    out.flush();
                }
            }catch(SocketException ex){
                try{
                    this.s.close();
                }catch(Exception ex2){
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente.");
            }catch(EOFException eofex){ 
                try{
                    this.s.close();
                }catch(Exception ex2){
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente.");
            }catch(IOException ioex){
                    System.out.println("ERROR: Hubo un error al intentar manejar el flujo de datos.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
} 

package casamatriz.servidor;

import casamatriz.FXMLDocumentController;
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
    String nombre = "Sucursal sin nombre";
    String estado;
    Servidor server;
    BaseDatos bd;
    // Constructor 
    public Worker(Socket s, Servidor server) { 
        this.s = s; 
        this.server = server;
        this.address = s.getInetAddress().getHostAddress();
        this.bd = BaseDatos.crearInstancia();
    } 
    
    public boolean isConnected(){
        boolean conectado = !this.s.isClosed();
        if(conectado)
            this.estado = "Conectado";
        else
            this.estado = "Desconectado";
        return conectado;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void cerrar(){
        try {
            this.s.close();
        } catch (IOException ex) {
            System.out.println("ERROR: No se puede cerrar la sucursal.");
        }
    }

    public String getEstado() {
        return estado;
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
        this.estado = "Conectado";
        while(true){
            try{
                if(!this.s.isClosed()){
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                    //if(in.available()>0) {
                        Object obj = in.readObject();
                        System.out.println("Objeto leido " + obj.getClass().toString());

                        if(obj instanceof String){
                            String cmd = (String)obj;
                            if(cmd.contains("Nombre:")){
                                String[] nombre = cmd.split(":");
                                System.out.println("Nombre de la sucursal: " + nombre[1]);
                                this.nombre = nombre[1];
                                this.server.updateSucursales();
                                this.bd.insertSucursal(nombre[1], address);
                            }else if(cmd.contains("actualizar_precios")){
                                enviar();
                            }else{
                                System.out.println("Mensaje: " + (String)obj);
                            }
                        }
                        if(obj instanceof Informacion){
                            Informacion info = (Informacion)obj;
                            System.out.println(info);
                        }

                        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject("OK");
                        out.flush();
                    }
                //}
            }catch(SocketException ex){
                try{
                    this.s.close();
                    this.estado = "Desconectado";
                    this.server.updateSucursales();
                }catch(Exception ex2){
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente.");
            }catch(EOFException eofex){ 
                try{
                    this.s.close();
                    this.estado = "Desconectado";
                    this.server.updateSucursales();
                }catch(Exception ex2){
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente.");
            }catch(IOException ioex){
                ioex.printStackTrace();
                    System.out.println("ERROR: Hubo un error al intentar manejar el flujo de datos.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
} 

package sucursal;


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
import java.util.ArrayList;
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
    BaseDeDatos bd;
    FXMLDocumentController controlador; 
  
    // Constructor 
    public Worker(Socket s ,BaseDeDatos base , FXMLDocumentController cont) { 
        this.s = s; 
        this.address = s.getInetAddress().getHostAddress();
        this.bd = base;
        this.controlador = cont;
    } 
    
    public boolean isConnected(){
        return !this.s.isClosed();
    }
    
    public String getAddress(){
        return this.address;
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
                      ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    System.out.println("Objeto leido " + obj.getClass().toString());
                    
                    if(obj instanceof String){
                        String str = (String)obj;
                        System.out.println("Mensaje: " + (String)obj);
                        switch(str){
                            case "actualizar_precios":
                                out.writeObject(InfoSurtidor.info);
                                break;
                        }
                    }
                    if(obj instanceof Informacion){
                        Informacion info = (Informacion)obj;
                        InfoSurtidor.info = info;
                        this.bd.actualizarPrecios(info);
                        this.bd.getPrecios();
                        System.out.println(info);
                    }else if(obj instanceof Transaccion){
                        Transaccion trans = (Transaccion)obj;
                        this.bd.insertTransaccion(trans.getTime(), trans.getTipoCombustible(), trans.getLitros(), trans.getPrecioPorLitro(), trans.getTotal(), trans.getRefSurtidor());
                        out.writeObject(InfoSurtidor.info);
                        this.actualizarCantidades(trans);
                        
                        ArrayList<Transaccion> transacciones = this.bd.getTransaccionesArray();
                        this.controlador.updateTransacciones(transacciones);
                        
                        
                        System.out.println(trans);
                    }
                    
                  
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
    
    private void actualizarCantidades(Transaccion trans) {
         switch(trans.getTipoCombustible()){
            case "93":
                InfoSurtidor.cantidad93+=trans.getLitros();
                InfoSurtidor.cargas93++;
                break;
            case "95":
                InfoSurtidor.cantidad95+=trans.getLitros();
                InfoSurtidor.cargas95++;
                break;
            case "97":
                InfoSurtidor.cantidad97+=trans.getLitros();
                InfoSurtidor.cargas97++;
                break;
            case "kerosene":
                InfoSurtidor.cantidadKerosene+=trans.getLitros();
                InfoSurtidor.cargasKerosene++;
                break;
            case "diesel":
                InfoSurtidor.cantidadDiesel+=trans.getLitros();
                InfoSurtidor.cargasDiesel++;
                break;
        }
    }

} 

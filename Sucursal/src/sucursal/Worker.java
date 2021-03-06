package sucursal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
    int id = 0;
    String idSurtidor;
    
    // Constructor 
    public Worker(Socket s, BaseDeDatos base, FXMLDocumentController cont) {
        this.s = s;
        this.address = s.getInetAddress().getHostAddress();
        this.bd = base;
        this.controlador = cont;

    }

    public boolean isConnected() {
        return !this.s.isClosed();
    }

    public String getAddress() {
        return this.address;
    }
    
        
    public void enviar(){
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(InfoSurtidor.info);
            out.flush();
        } catch (IOException ex) {
            System.out.println("ERROR: No se pudo enviar los precios actualizados.");
        }
    }

    public void check() {
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
        boolean primer = true;
        String received = "",
                toreturn = "";
        if (this.s.isConnected()) {

            System.out.println("[" + this.s.getInetAddress().getHostAddress() + "] Surtidor Conectado.");
            ArrayList<String> ips = this.getLocalIPs();
            for (String ip : ips) {
                System.out.println("ips: " + ip);
            }
            for (String ip : ips) {
                if (bd.checkSucursal(ip) != -1) {
                    id = bd.checkSucursal(ip);

                }
            }
            System.out.println("ip entrante: " + id);
        }
        
        
        try {
            ObjectOutputStream dos;
            dos = new ObjectOutputStream(s.getOutputStream());
            ArrayList<Falla> fallas = bd.getFallasSurtidor();
            System.out.println(fallas.size());
            dos.writeObject(fallas);
            dos.flush();
            
            ObjectOutputStream op;
            op = new ObjectOutputStream(s.getOutputStream());
            Informacion info = InfoSurtidor.info;
            op.writeObject(info);
            op.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
                            

        
        while (true) {

            try {
               

                if (!this.s.isClosed()) {
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                    //if(in.available()>0) {
                    Object obj = in.readObject();
                    System.out.println("Objeto leido " + obj.getClass().toString());

                    if (obj instanceof String) {
                        String str = (String) obj;
                        System.out.println("Mensaje: " + (String) obj);

                        if (str.contains("ID:")) {
                            String[] nombre = str.split(":");
                            System.out.println("id del surtidor: " + nombre[1]);
                            this.idSurtidor = nombre[1];
                            
                        }

                        switch (str) {
                            case "actualizar_precios":
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                out.writeObject(InfoSurtidor.info);
                                out.flush();
                                break;
                        }
                    }
                    if (obj instanceof Informacion) {
                        Informacion info = (Informacion) obj;
                        System.out.println(info);
                    }
                    if (obj instanceof Transaccion) {
                        System.out.println("entra");
                        Transaccion trans = (Transaccion) obj;
                        System.out.println(trans.getTipoCombustible());

                        this.bd.insertTransaccion(trans.getTime(), trans.getTipoCombustible(), trans.getLitros(), trans.getPrecioPorLitro(), trans.getTotal(), id,this.idSurtidor);
                        //out.writeObject(InfoSurtidor.info);
                        this.actualizarCantidades(trans);

                        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject("actualizar");
                        out.flush();

                        ArrayList<Transaccion> transacciones = this.bd.getTransaccionesArray();
                        this.controlador.updateTransacciones(transacciones);

                        System.out.println(">" + trans);
                    }
                    
                   

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject("OK");
                    out.flush();
                }
                //}
            } catch (SocketException ex) {
                try {
                    this.s.close();

                } catch (Exception ex2) {
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente1.");

                if (InfoSurtidor.caida == false) {
                    InfoSurtidor.comienzo = new Timestamp(new java.util.Date().getTime());
                    InfoSurtidor.refSurtidor = this.idSurtidor;
                    InfoSurtidor.caida = true;
                }

            } catch (EOFException eofex) {
                try {
                    this.s.close();

                } catch (Exception ex2) {
                    System.out.println("ERROR: No se puede cerrar la conexion con el socket " + getAddress());
                }
                System.out.println("ERROR: La sucursal " + getAddress() + " se cerro inesperadamente2.");
            } catch (IOException ioex) {
                ioex.printStackTrace();
                System.out.println("ERROR: Hubo un error al intentar manejar el flujo de datos.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ArrayList<String> getLocalIPs() {
        ArrayList<String> ips = new ArrayList<String>();
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();

                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (i instanceof Inet4Address) {
                        ips.add(i.getHostAddress());
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("ERROR: No se pudo obtener las IPs locales.");
        }
        return ips;
    }

    private void actualizarCantidades(Transaccion trans) {
        switch (trans.getTipoCombustible()) {
            case "93":
                InfoSurtidor.cantidad93 += trans.getLitros();
                InfoSurtidor.cargas93++;
                break;
            case "95":
                InfoSurtidor.cantidad95 += trans.getLitros();
                InfoSurtidor.cargas95++;
                break;
            case "97":
                InfoSurtidor.cantidad97 += trans.getLitros();
                InfoSurtidor.cargas97++;
                break;
            case "kerosene":
                InfoSurtidor.cantidadKerosene += trans.getLitros();
                InfoSurtidor.cargasKerosene++;
                break;
            case "diesel":
                InfoSurtidor.cantidadDiesel += trans.getLitros();
                InfoSurtidor.cargasDiesel++;
                break;
        }
    }

}

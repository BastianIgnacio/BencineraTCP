/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz.servidor;

import casamatriz.SharedInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import casamatriz.modelos.Informacion;
import casamatriz.modelos.Reporte;
import casamatriz.modelos.Sucursal;

/**
 *
 * @author moris
 */
/**
 *
 * @author macbook, moris
 */
public class BaseDatos 
{
    
    private Connection con;
    private static BaseDatos bd;

    // El constructor es privado, no permite que se genere un constructor por defecto.
    private BaseDatos() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://3.14.93.219:3306/bencinera", "distribuidos", "distribuidos");
        } catch (Exception ex) {
            System.out.println("ERROR: Problemas al abrir conexion con la base de datos.");
            System.out.println(ex);
        }
    }

    public static BaseDatos crearInstancia() {
        if (bd == null){
            bd = new BaseDatos();
        }else{
            System.out.println("ERROR: No se puede crear el objeto porque ya existe un objeto de la clase BaseDeDatos, se obtendra esta instancia.");
        }
        
        return bd;
    }
    
    public void cerrar(){
        try{
            this.con.close();
        }catch(Exception ex){
            System.out.println("ERROR: No se pudo cerrar la conexion a la base de datos.");
        }
    }
    
    public boolean checkConexion(){
        try {
            if(!this.con.isClosed()){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException ex) {
            System.out.println("ERROR: ");
        }
        return false;
    }
    
    private int getIdTransaccion(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Transaccion ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            return (rs.getInt(1))+1;
        }catch(Exception ex){
            System.out.println(ex);
            return 1;
        }
    }
    
    public void insertTransaccion(Timestamp fecha, String tipoCombustible, long litros,long precioLitro,long total, int surtidor){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            String str = "INSERT INTO Transaccion VALUES(?,?,?,?,?,?,?)";
            ps = con.prepareStatement(str);
            ps.setInt(1, getIdTransaccion());
            ps.setTimestamp(2, fecha);
            ps.setString(3, tipoCombustible);
            ps.setLong(4, litros);
            ps.setLong(5, precioLitro);
            ps.setLong(6, total);
            ps.setString(7, String.valueOf(surtidor));
            if(ps.execute()){
                System.out.println("INFO: Transaccion insertada exitosamente.");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private int getIdSucursal(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Sucursal ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return (rs.getInt(1))+1;
            }else{
                return 1;
            }
        }catch(Exception ex){
            System.out.println(ex);
            return 1;
        }
    }
    
    private int checkSucursal(String ip){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Sucursal WHERE ip='" + ip +"' LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1);
            } else {
                return -1;
            }
        }catch(Exception ex){
            System.out.println("ERROR: Problema al intentar verificar la existencia de la sucursal.");
            return -2;
        }
    }
    
    
    public void insertSucursal(String nombre, String ip){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            if(checkSucursal(ip)==-1){
                String str = "INSERT INTO Sucursal VALUES(?,?,?)";
                ps = con.prepareStatement(str);
                ps.setInt(1, getIdSucursal());
                ps.setString(2, nombre);
                ps.setString(3, ip);
                if(ps.execute()){
                    System.out.println("INFO: Sucursal creada exitosamente.");
                }
            }
        }catch(Exception ex){
            System.out.println("ERROR: Problema al intentar agregar la sucursal.");
        }
    }
    
    public int checkPrecios(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy"); 
                Date date = new Date();
            String sql = "SELECT id FROM Precios WHERE fecha='" + fecha.format(date)+"' LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1);
            }
            else {
                return -1;
            }
        }catch(Exception ex){
            System.out.println("ERROR: No se pudo obtener el ultimo precio almacenado.");
            return -2;
        }
             
    }
    
    private int getIdPrecios(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Precios ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1)+1;
                
            }
            else {
                return 0;
            }
        }catch(Exception ex){
            System.out.println("ERROR: No se pudo obtener el identificar correspondiente al siguiente precio.");
            return -1;
        }
    }
    
    public ArrayList<Reporte> getReporteSucursal(int id){
        Statement stmt = null;
        ArrayList<Reporte> reportes = new ArrayList<Reporte>();
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT tipoCombustible, COUNT(id) as cargas, SUM(litros) as total_lts, SUM(total) as total_venta FROM Transaccion WHERE ref_sucursal=" + id +" AND DATE(fecha) = DATE(NOW()) GROUP BY tipoCombustible;";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Reporte reporte = new Reporte(rs.getString(1), rs.getLong(2), rs.getLong(3), rs.getLong(4));
                reportes.add(reporte);
                //System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6) + " - " + rs.getString(7));
            }
        }catch(Exception ex){
            System.out.println("ERROR: No se pudo obtener el identificar correspondiente al siguiente precio.");
        }
        return reportes;
    }
    
    public ArrayList<Sucursal> getSucursales(){
        Statement stmt = null;
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT * FROM Sucursal";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Sucursal suc = new Sucursal(rs.getLong(1), rs.getString(2), rs.getString(3));
                sucursales.add(suc);
                //System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6) + " - " + rs.getString(7));
            }
        }catch(Exception ex){
            System.out.println(ex);
            System.out.println("ERROR: No se pudo obtener las sucursales.");
        }
        return sucursales;
    }
    
    public void actualizarPrecios(Informacion info){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            int id = checkPrecios();
            if(id == -1){
                System.out.println("INFO: Se ingresaron los precios en la base de datos.");
                int idprecios = getIdPrecios();
                String str = "INSERT INTO Precios VALUES(?,?,?,?,?,?,?)";
                ps = con.prepareStatement(str);
                
                ps.setInt(1, idprecios);
                ps.setLong(2, (long)(info.getBencina93())); // 93
                ps.setLong(3, (long)(info.getBencina95())); // 95
                ps.setLong(4, (long)(info.getBencina97())); // 97
                ps.setLong(5, (long)(info.getDiesel())); // diesel
                ps.setLong(6, (long)(info.getKerosene())); // kerosene
                DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy"); 
                Date date = new Date();
                ps.setString(7, fecha.format(date) );
                if(ps.execute()){
                    System.out.println("INFO: Precios insertados exitosamente.");
                }
            }else{
                System.out.println("INFO: Se modificaron los precios en la base de datos.");
                Date date = new Date(new java.util.Date().getTime());
                String str = "UPDATE Precios SET precio93=?, precio95=?, precio97=?, precioDiesel=?, precioKerosene=? WHERE id=" + id;
                ps = con.prepareStatement(str);
                ps.setLong(1, (long)(info.getBencina93())); // 93
                ps.setLong(2, (long)(info.getBencina95())); // 95
                ps.setLong(3, (long)(info.getBencina97())); // 97
                ps.setLong(4, (long)(info.getDiesel())); // diesel
                ps.setLong(5, (long)(info.getKerosene())); // kerosene
                if(ps.execute()){
                    System.out.println("INFO: Precios actualizados exitosamente.");
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public ArrayList<Informacion> getPrecios(){
        Statement stmt = null;
        ArrayList<Informacion> infos = new ArrayList<Informacion>();
        try {
            stmt = this.con.createStatement();
            Date date = new Date(new java.util.Date().getTime());
            String sql = "SELECT * FROM Precios;";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Informacion info = new Informacion((int)rs.getLong(2),(int)rs.getLong(3),(int)rs.getLong(4),(int)rs.getLong(5),(int)rs.getLong(6));
                info.setFecha(rs.getString(7));
                SharedInfo.info = info;
                infos.add(info);
                //System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6) + " - " + rs.getString(7));
            }
        }catch(Exception ex){
            System.out.println("ERROR: No se pudo obtener los precios.");
        }
        return infos;
    }
}
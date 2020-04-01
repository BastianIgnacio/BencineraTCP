/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz.servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("ERROR: No se puede crear el objeto porque ya existe un objeto de la clase BaseDeDatos");
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
}
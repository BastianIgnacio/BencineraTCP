/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macbook, moris
 */
public class BaseDeDatos 
{
    
    private Connection con;
    private static BaseDeDatos bd;

    // El constructor es privado, no permite que se genere un constructor por defecto.
    private BaseDeDatos() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.con = DriverManager.getConnection("jdbc:sqlite:bencinera.db");
        } catch (Exception ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BaseDeDatos crearInstancia() {
        if (bd == null){
            bd = new BaseDeDatos();
        }
        else{
            System.out.println("No se puede crear el objeto porque ya existe un objeto de la clase BaseDeDatos");
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
    
    public void insertSurtidor(long precio93,long precio95, long precio97,long precioDiesel,long precioKerosene){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            String str = "INSERT INTO Surtidor VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(str);
            ps.setInt(1, getIdSurtidor());
            ps.setLong(2, precio93);
            ps.setLong(3, precio95);
            ps.setLong(4, precio97);
            ps.setLong(5, precioDiesel);
            ps.setLong(6, precioKerosene);
            if(ps.execute()){
                System.out.println("INFO: Surtidor insertado exitosamente.");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void getSurtidores(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT * FROM Surtidor";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private int getIdSurtidor(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Surtidor ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            return (rs.getInt(1))+1;
        }catch(Exception ex){
            System.out.println(ex);
            return -1;
        }
    }
        public void getTransacciones(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT * FROM Transaccion";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
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
            return -1;
        }
    }
    
    public void insertTransaccion(Date fecha, long tipoCombustible, long litros,long precioLitro,long total, int surtidor){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            String str = "INSERT INTO Transaccion VALUES(?,?,?,?,?,?,?)";
            ps = con.prepareStatement(str);
            ps.setInt(1, getIdTransaccion());
            ps.setDate(2, fecha);
            ps.setLong(3, tipoCombustible);
            ps.setLong(4, litros);
            ps.setLong(5, precioLitro);
            ps.setLong(6, total);
            ps.setInt(7, surtidor);
            if(ps.execute()){
                System.out.println("INFO: Transaccion insertada exitosamente.");
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public boolean checkSurtidor(int surtidor){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Surtidor WHERE id=" + surtidor ;
            
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.isBeforeFirst()){
                return false;
            }
            else {
                return true;
            }
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public void crearTabla(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "CREATE TABLE \"Surtidor\" (\n" +
                        "    id int NOT NULL,\n" +
                        "    precio93 bigint,\n" +
                        "    precio95 bigint,\n" +
                        "    precio97 bigint,\n" +
                        "    \"precioDiesel\" bigint,\n" +
                        "    \"precioKerosene\" bigint,\n" +
                        "    CONSTRAINT \"Surtidor_pkey\" PRIMARY KEY (\"id\")\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE \"Transaccion\" (\n" +
                        "    id int NOT NULL,\n" +
                        "    fecha timestamp without time zone,\n" +
                        "    \"tipoCombustible\" text,\n" +
                        "    litros bigint,\n" +
                        "    \"precioPorLitro\" bigint,\n" +
                        "    total bigint,\n" +
                        "    \"refSurtidor\" int,\n" +
                        "    CONSTRAINT \"Transaccion_pkey\" PRIMARY KEY (\"id\"),\n" +
                        "    CONSTRAINT \"Transaccion_refSurtidor_fkey\" FOREIGN KEY (\"refSurtidor\") REFERENCES \"Surtidor\"(\"id\")\n" +
                        ");"; 
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("INFO: Tablas creadas con exto");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macbook
 */
public class BaseDeDatos 
{
    
    private Connection con;
    private static BaseDeDatos bd;

    // El constructor es privado, no permite que se genere un constructor por defecto.
    private BaseDeDatos() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.con = DriverManager.getConnection("jdbc:sqlite:test.db");
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
    
    public void getTransacciones(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT * FROM Transaccion";
            
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("QUERY EJECUTADA");
            while(rs.next()){
                System.out.println(rs.getInt(1) + " - " + rs.getDate(2));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void insertSurtidor(long precio93,long precio95, long precio97,long precioDiesel,long precioKerosene){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            String str = "INSERT INTO Surtidor VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(str);
            ps.setString(1, String.valueOf(getIdSurtidor()));
            ps.setLong(2, precio93);
            ps.setLong(3, precio95);
            ps.setLong(4, precio97);
            ps.setLong(5, precioDiesel);
            ps.setLong(6, precioKerosene);
            if(ps.execute()){
                System.out.println("SURTIDOR INSERTADO");
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
            System.out.println("QUERY EJECUTADA");
            while(rs.next()){
                System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public int getIdSurtidor(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT idSurtidor FROM Surtidor ORDER BY idSurtidor DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("QUERY EJECUTADA");
            return Integer.parseInt(rs.getString(1))+1;
        }catch(Exception ex){
            System.out.println(ex);
            return -1;
        }
    }
    
    public void crearTabla(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "CREATE TABLE \"Surtidor\" (\n" +
"    \"idSurtidor\" text NOT NULL,\n" +
"    precio93 bigint,\n" +
"    precio95 bigint,\n" +
"    precio97 bigint,\n" +
"    \"precioDiesel\" bigint,\n" +
"    \"precioKerosene\" bigint,\n" +
"    CONSTRAINT \"Surtidor_pkey\" PRIMARY KEY (\"idSurtidor\")\n" +
");\n" +
"\n" +
"CREATE TABLE \"Trabajador\" (\n" +
"    rut text NOT NULL,\n" +
"    nombre text,\n" +
"    apellido text,\n" +
"    cargo text,\n" +
"    CONSTRAINT \"Trabajador_pkey\" PRIMARY KEY (rut)\n" +
");\n" +
"\n" +
"CREATE TABLE \"Transaccion\" (\n" +
"    \"idTransaccion\" text NOT NULL,\n" +
"    fecha timestamp without time zone,\n" +
"    \"tipoCombustible\" text,\n" +
"    litros bigint,\n" +
"    \"precioPorLitro\" bigint,\n" +
"    total bigint,\n" +
"    \"refSurtidor\" text,\n" +
"    \"refTrabajador\" text,\n" +
"    CONSTRAINT \"Transaccion_pkey\" PRIMARY KEY (\"idTransaccion\"),\n" +
"    CONSTRAINT \"Transaccion_refSurtidor_fkey\" FOREIGN KEY (\"refSurtidor\") REFERENCES \"Surtidor\"(\"idSurtidor\"),\n" +
"    CONSTRAINT \"Transaccion_refTrabajador_fkey\" FOREIGN KEY (\"refTrabajador\") REFERENCES \"Trabajador\"(rut)\n" +
");"; 
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}

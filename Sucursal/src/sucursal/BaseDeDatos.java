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
import java.sql.Timestamp;
import java.util.ArrayList;
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
                
                System.out.println(rs.getLong(2) + " - " + rs.getString(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
        
    public ArrayList<Transaccion> getTransaccionesArray()
    {
        ArrayList<Transaccion> trans = new ArrayList();
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT * FROM Transaccion";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                Transaccion tran = new Transaccion(rs.getTimestamp(2), rs.getString(3), (int)rs.getLong(4),(int)rs.getLong(5),(int)rs.getLong(6),(int)rs.getLong(7));
                System.out.println(tran);
               
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return trans;
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
    
    public void actualizarPrecios(Informacion info){
        Statement stmt = null;
        PreparedStatement ps = null;
        try {
            int id = checkPrecios();
            System.out.println("ID ENCONTRADO " + id);
            if(id == -1){
                System.out.println("Entro a ingresar preicos");
                int idprecios = getIdPrecios();
                String str = "INSERT INTO Precios VALUES(?,?,?,?,?,?,?)";
                ps = con.prepareStatement(str);
                
                ps.setInt(1, idprecios);
                ps.setLong(2, info.getBencina93()); // 93
                ps.setLong(3, info.getBencina95()); // 95
                ps.setLong(4, info.getBencina97()); // 97
                ps.setLong(5, info.getDiesel()); // diesel
                ps.setLong(6, info.getKerosene()); // kerosene
                Date date = new Date(new java.util.Date().getTime());
                ps.setString(7, date.toString());
                if(ps.execute()){
                    System.out.println("INFO: Precios insertados exitosamente.");
                }
            }else{
                System.out.println("Entro a actualizar precios");
                Date date = new Date(new java.util.Date().getTime());
                String str = "UPDATE Precios SET precio93=?, precio95=?, precio97=?, precioDiesel=?, precioKerosene=? WHERE id=" + id;
                ps = con.prepareStatement(str);
                ps.setLong(1, info.getBencina93()); // 93
                ps.setLong(2, info.getBencina95()); // 95
                ps.setLong(3, info.getBencina97()); // 97
                ps.setLong(4, info.getDiesel()); // diesel
                ps.setLong(5, info.getKerosene()); // kerosene
                if(ps.execute()){
                    System.out.println("INFO: Precios actualizados exitosamente.");
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    private int getIdPrecios(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            String sql = "SELECT id FROM Precios ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.isBeforeFirst()){
                return 0;
            }
            else {
                return rs.getInt(1)+1;
            }
        }catch(Exception ex){
            System.out.println(ex);
            return -1;
        }
    }
    
    public int checkPrecios(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            Date date = new Date(new java.util.Date().getTime());
            String sql = "SELECT id FROM Precios WHERE fecha='" + date.toString()+"' LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs.getInt(1));
            if(!rs.isBeforeFirst()){
                return -1;
            }
            else {
                return rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println(ex);
            return -1;
        }
             
    }
    
    public void getPrecios(){
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            Date date = new Date(new java.util.Date().getTime());
            String sql = "SELECT * FROM Precios WHERE fecha='" + date.toString()+"' LIMIT 1;";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Informacion info = new Informacion((int)rs.getLong(2),(int)rs.getLong(3),(int)rs.getLong(4),(int)rs.getLong(5),(int)rs.getLong(6));
                InfoSurtidor.info = info;
                System.out.println(InfoSurtidor.info);
                //System.out.println(rs.getLong(2) + " - " + rs.getLong(3) + " - " + rs.getLong(4) + " - " + rs.getLong(5) + " - " + rs.getLong(6) + " - " + rs.getString(7));
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
"    \"refSurtidor\" text,\n" +
"    CONSTRAINT \"Transaccion_pkey\" PRIMARY KEY (\"id\")\n" +
");\n" +
"\n" +
"CREATE TABLE \"Precios\" (\n" +
"	id int,\n" +
"	precio93 bigint,\n" +
"    precio95 bigint,\n" +
"    precio97 bigint,\n" +
"    \"precioDiesel\" bigint,\n" +
"    \"precioKerosene\" bigint,\n" +
"    fecha text,\n" +
"    CONSTRAINT \"Precios_pkey\" PRIMARY KEY (\"id\")\n" +
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

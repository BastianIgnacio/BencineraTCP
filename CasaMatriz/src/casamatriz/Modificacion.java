/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author macbook
 */
public class Modificacion {

    public Modificacion(String tipo,Timestamp fecha, int precio) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    private String tipo;
    private Timestamp fecha;
    private int precio;
    
    
    
    
}

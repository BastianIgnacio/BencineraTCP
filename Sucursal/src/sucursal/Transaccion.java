/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author macbook
 */
public class Transaccion implements Serializable{

    private static final long serialVersionUID = 6529685098267757690L;
    private Timestamp time;
    private String id;
    private String tipoCombustible;
    private int litros;
    private int precioPorLitro;
    private int total;
    
    public Transaccion(Timestamp time, String id, String tipoCombustible, int litros, int precioPorLitro, int total) {
        this.time = time;
        this.id = id;
        this.tipoCombustible = tipoCombustible;
        this.litros = litros;
        this.precioPorLitro = precioPorLitro;
        this.total = total;
    }

    Transaccion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public int getLitros() {
        return litros;
    }

    public void setLitros(int litros) {
        this.litros = litros;
    }

    public int getPrecioPorLitro() {
        return precioPorLitro;
    }

    public void setPrecioPorLitro(int precioPorLitro) {
        this.precioPorLitro = precioPorLitro;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}

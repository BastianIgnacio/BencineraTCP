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
    private String tipoCombustible;
    private int litros;
    private int precioPorLitro;
    private int total;
    private int refSurtidor;
    
    public Transaccion(Timestamp time, String tipoCombustible, int litros, int precioPorLitro, int total, int refSurtidor) {
        this.time = time;
        this.tipoCombustible = tipoCombustible;
        this.litros = litros;
        this.precioPorLitro = precioPorLitro;
        this.total = total;
        this.refSurtidor = refSurtidor;
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

    public int getRefSurtidor() {
        return refSurtidor;
    }

    public void setRefSurtidor(int refSurtidor) {
        this.refSurtidor = refSurtidor;
    }

    @Override
    public String toString() {
        return "Transaccion{" + "time=" + time + ", tipoCombustible=" + tipoCombustible + ", litros=" + litros + ", precioPorLitro=" + precioPorLitro + ", total=" + total + ", refSurtidor=" + refSurtidor + '}';
    }
    
}

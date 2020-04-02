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
 * @author moris
 */
public class Falla implements Serializable{
    private static final long serialVersionUID = 6529685094467757690L;
    private Timestamp caida;
    private Timestamp reconexion;
    private long tiempo_caido;
    private String surtidor;

    public Timestamp getCaida() {
        return caida;
    }

    public void setCaida(Timestamp caida) {
        this.caida = caida;
    }

    public Timestamp getReconexion() {
        return reconexion;
    }

    public void setReconexion(Timestamp reconexion) {
        this.reconexion = reconexion;
    }

    public long getTiempo_caido() {
        return (reconexion.getTime() - caida.getTime())/1000;
    }

    public String getSurtidor() {
        return surtidor;
    }

    public void setSurtidor(String surtidor) {
        this.surtidor = surtidor;
    }

    @Override
    public String toString() {
        return "Falla{" + "caida=" + caida + ", reconexion=" + reconexion + ", tiempo_caido=" + tiempo_caido + '}';
    }
    
    
    
}

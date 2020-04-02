/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

/**
 *
 * @author moris
 */
public class Reporte {
    private String tipoCombustbiel;
    private long cargas;
    private long litros;
    private long ventas;

    public Reporte(String tipoCombustbiel, long cargas, long litros, long ventas) {
        this.tipoCombustbiel = tipoCombustbiel;
        this.cargas = cargas;
        this.litros = litros;
        this.ventas = ventas;
    }
    
    public String getTipoCombustbiel() {
        return tipoCombustbiel;
    }

    public void setTipoCombustbiel(String tipoCombustbiel) {
        this.tipoCombustbiel = tipoCombustbiel;
    }

    public long getCargas() {
        return cargas;
    }

    public void setCargas(int cargas) {
        this.cargas = cargas;
    }

    public long getLitros() {
        return litros;
    }

    public void setLitros(int litros) {
        this.litros = litros;
    }

    public long getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Reporte{" + "tipoCombustbiel=" + tipoCombustbiel + ", cargas=" + cargas + ", litros=" + litros + ", ventas=" + ventas + '}';
    }
    
    
    
}

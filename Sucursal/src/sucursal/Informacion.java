/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.io.Serializable;

/**
 *
 * @author moris
 */
public class Informacion implements Serializable{
    private static final long serialVersionUID = 6529685098267757545L;
    private int bencina93;
    private int bencina95;
    private int bencina97;
    private int diesel;
    private int kerosene;

    public Informacion(int bencina93, int bencina95, int bencina97, int diesel, int kerosene) {
        this.bencina93 = bencina93;
        this.bencina95 = bencina95;
        this.bencina97 = bencina97;
        this.diesel = diesel;
        this.kerosene = kerosene;
    }

    public int getBencina93() {
        return bencina93;
    }

    public void setBencina93(int bencina93) {
        this.bencina93 = bencina93;
    }

    public int getBencina95() {
        return bencina95;
    }

    public void setBencina95(int bencina95) {
        this.bencina95 = bencina95;
    }

    public int getBencina97() {
        return bencina97;
    }

    public void setBencina97(int bencina97) {
        this.bencina97 = bencina97;
    }

    public int getDiesel() {
        return diesel;
    }

    public void setDiesel(int diesel) {
        this.diesel = diesel;
    }

    public int getKerosene() {
        return kerosene;
    }

    public void setKerosene(int kerosene) {
        this.kerosene = kerosene;
    }

    @Override
    public String toString() {
        return "Informacion{" + "bencina93=" + bencina93 + ", bencina95=" + bencina95 + ", bencina97=" + bencina97 + ", diesel=" + diesel + ", kerosene=" + kerosene + '}';
    }
    
    
}

package surtidorr;


import java.util.ArrayList;
import sucursal.Falla;
import sucursal.Informacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author moris
 */
public class SharedInfo {
    public static Informacion info;
    /* IP DE LA SUCURSAL */
    public static String idSurtidor = "";
    public static String ipSucursal = "127.0.0.1";
    public static ArrayList<Falla> fallas = new ArrayList<>();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import casamatriz.modelos.Informacion;

/**
 *
 * @author moris
 */
public class FileHandler {
    public static void loadInfo(){
        try {
            FileInputStream fileIn = new FileInputStream("ultimainfo.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Informacion info = (Informacion)objectIn.readObject();
            SharedInfo.info = info;
            objectIn.close();
            System.out.println("INFO: Se cargó la última información de los combustibles.");
        }catch(IOException ex){
            System.out.println("ERROR: No se pudo abrir el archivo de información.");
            SharedInfo.info = new Informacion(0,0,0,0,0);
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: Tipo de objeto no encontrado.");
        }
    }
    
    public static void saveInfo(){
        try {
            FileOutputStream fileOut = new FileOutputStream("ultimainfo.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(SharedInfo.info);
            objectOut.close();
            System.out.println("INFO: Se guardo la última información de los combustibles.");
        }catch(IOException ex){
            System.out.println("ERROR: No se pudo crear el archivo de información.");
        }
    }
}

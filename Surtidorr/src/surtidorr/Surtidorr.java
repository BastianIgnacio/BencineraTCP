/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surtidorr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sucursal.Informacion;

/**
 *
 * @author roduc
 */
public class Surtidorr extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            File tempFile = new File("config.properties");
            boolean exists = tempFile.exists();
            if(exists){
                InputStream input = new FileInputStream("config.properties");
                Properties prop = new Properties();
                prop.load(input);
                SharedInfo.idSurtidor = prop.getProperty("surtidor.id");
                SharedInfo.ipSucursal = prop.getProperty("surtidor.ip.sucursal");
            }else{
                OutputStream output = new FileOutputStream("config.properties");

                Properties prop = new Properties();
                String idSurtidor = String.valueOf(System.currentTimeMillis() / 1000L);
                prop.setProperty("surtidor.id", idSurtidor);
                prop.setProperty("surtidor.ip.sucursal", "127.0.0.1");
                SharedInfo.idSurtidor = idSurtidor;
                SharedInfo.ipSucursal = "127.0.0.1";

                prop.store(output, null);
            }
            System.out.println("INFO: Archivo de configuracion cargado exitosamente.");
        } catch (IOException ex) {
             System.out.println("ERROR: No se puede abrir el archivo de configuracion.");
             System.exit(0);
        }
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        // Se inicia hilo con comando de actualizacion de precios.
       
        
       
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("INFO: Cerrando servidor sockets.");
              System.exit(0);
          }
      });   
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

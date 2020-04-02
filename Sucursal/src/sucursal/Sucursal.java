/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author macbook
 */
public class Sucursal extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            InfoSurtidor.nombreSucursal = prop.getProperty("sucursal.nombre");
            InfoSurtidor.ipPrimaria = prop.getProperty("sucursal.ip.primaria");
            InfoSurtidor.ipSecundaria = prop.getProperty("sucursal.ip.secundaria");
             System.out.println("INFO: Archivo de configuracion cargado exitosamente.");

        } catch (IOException ex) {
             System.out.println("ERROR: No se puede abrir el archivo de configuracion.");
             System.exit(0);
        }
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        BaseDeDatos bd = BaseDeDatos.crearInstancia();
        //bd.crearTabla();
        InfoSurtidor.margen = 0;
        bd.getPrecios();
        Scene scene = new Scene(root);
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

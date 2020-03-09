/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import sucursal.Transaccion;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import sucursal.Informacion;

/**
 *
 * @author macbook
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button modificar_93;
    @FXML
    private Button modificar_95;
    @FXML
    private Button modificar_97;
    @FXML
    private Button modificar_kerosene;
    @FXML
    private Button modificar_diesel;
    
    
    
    // Parte logica
    
    private int precio_93;
    private int precio_95;
    private int precio_97;
    private int precio_diesel;
    private int precio_kerosene;
    @FXML
    private Button preciosActuales;
    @FXML
    private Button establecer_cliente;
    @FXML
    private BorderPane panelCentro;
    
    
    private Informacion info; 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.info = new Informacion(0,0,0,0,0);
    }    

    @FXML
    private void buttonAction(ActionEvent event) throws ClassNotFoundException {
        
        if(event.getSource()==this.establecer_cliente)
        {
            
        
        }
        if(event.getSource()==this.preciosActuales){
            System.out.println(this.info);
        }
        if(event.getSource()==this.modificar_93)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 93 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 93 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 93 => " + precio);
                this.info.setBencina93(precio);
                Cliente cliente = new Cliente();
                cliente.setInformacion(info);
                new Thread(cliente).start();
            }
        }
        if(event.getSource()==this.modificar_95)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 95 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 95 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 95 => " + precio);
                this.info.setBencina95(precio);
                Cliente cliente = new Cliente();
                cliente.setInformacion(info);
                new Thread(cliente).start();
            }
        
        }
        if(event.getSource()==this.modificar_97)
        {
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 97 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 97 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 97 => " + precio);
                this.info.setBencina97(precio);
                Cliente cliente = new Cliente();
                cliente.setInformacion(info);
                new Thread(cliente).start();
            }
        }
        if(event.getSource()==this.modificar_kerosene)
        {
        
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de kerosene");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para el kerosene:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio kerosene => " + precio);
                this.info.setKerosene(precio);
                Cliente cliente = new Cliente();
                cliente.setInformacion(info);
                new Thread(cliente).start();
            }
        }
        if(event.getSource()==this.modificar_diesel)
        {
        
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de diesel");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para el diesel es:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio diesel=> " + precio);
                this.info.setDiesel(precio);
                Cliente cliente = new Cliente();
                cliente.setInformacion(info);
                new Thread(cliente).start();
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

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
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonAction(ActionEvent event) {
        
        if(event.getSource()==this.establecer_cliente)
        {
            this.establecerConeccionCliente();
        
        }
        if(event.getSource()==this.modificar_93)
        {
            System.out.println("modificar 93");
        
        }
        if(event.getSource()==this.modificar_95)
        {
             System.out.println("modificar 95");
        
        }
        if(event.getSource()==this.modificar_97)
        {
             System.out.println("modificar 97");
        }
        if(event.getSource()==this.modificar_kerosene)
        {
        
             System.out.println("modificar kerosene");
        }
        if(event.getSource()==this.modificar_diesel)
        {
        
             System.out.println("modificar diesel");
        }
    }
    
    private void establecerConeccionCliente()
    {
        final String HOST = "127.0.0.1";
        final int PUERTO = 5000;
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            Socket sc = new Socket(HOST,PUERTO);
            in = new ObjectInputStream(sc.getInputStream());
            out = new ObjectOutputStream(sc.getOutputStream());
            
            sc.close();
            System.out.println("Cliente cerrado");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
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

/**
 *
 * @author macbook
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    private Button transacciones;
    @FXML
    private Button trabajadores;
    @FXML
    private Button precios;
    @FXML
    private Button iniciarServidor;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }    

    @FXML
    private void buttonAction(ActionEvent event) {
        if(event.getSource()==this.iniciarServidor)
        {
            iniciarServidor();
        }
        if(event.getSource()==this.transacciones)
        {
            System.out.println("transacciones");
        }
        if(event.getSource()==this.precios)
        {
            System.out.println("precios");
        }
    }
    
    private void iniciarServidor()
    {
        ServerSocket servidor = null;
        Socket sc = null;
        final int PUERTO = 5000;
        
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado");
            while(true)
            {
                System.out.println("Esperando..");
                sc = servidor.accept();
                in = new ObjectInputStream(sc.getInputStream());
                
                
                
                sc.close();
                System.out.println("servidor cerrado");
            } 
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

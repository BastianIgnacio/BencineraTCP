/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
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
        iniciarServidor();
      
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
            BaseDeDatos bd = BaseDeDatos.crearInstancia();
            bd.insertSurtidor(1, 2, 3, 4, 5);
            bd.getSurtidores();
        }
    }
    
    private void iniciarServidor()
    {
        Thread a = new Thread(new Servidor());
        a.start();
    }
    
    private Transaccion crear()
    {
        Timestamp time = null;
        String idTransaccion = "11";
        String tipoCombustible = "diesel";
        int litros = 15;
        int precioPorLitro= 500;
        int total=15000;
        
        Transaccion t = new Transaccion(time,idTransaccion,tipoCombustible,litros,precioPorLitro,total);
        return t;
    }
}

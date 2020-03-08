/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surtidorr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Integer.parseInt;
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
import javafx.scene.control.TextField;
import sucursal.Transaccion;

/**
 *
 * @author roduc
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private TextField field93;
    @FXML private TextField field95;
    @FXML private TextField field97;
    @FXML private Button boton93;
    @FXML private Button boton95;
    @FXML private Button boton97;
    
    @FXML
    private void buttonAction(ActionEvent event) throws ClassNotFoundException {
        
        if(event.getSource()==this.boton93)
        {
            System.out.println(field93.getText());
            Transaccion t = this.crear("93",parseInt(field93.getText()));
            Cliente cliente = new Cliente();
            cliente.setTransaccion(t);
            new Thread(cliente).start();
            field93.setText("");
        }
        
        if(event.getSource()==this.boton95)
        {
            System.out.println(field95.getText());
            Transaccion t = this.crear("95",parseInt(field95.getText()));
            Cliente cliente = new Cliente();
            cliente.setTransaccion(t);
            new Thread(cliente).start();
            field95.setText("");
        }
        
        if(event.getSource()==this.boton97)
        {
            System.out.println(field97.getText());
            Transaccion t = this.crear("97",parseInt(field97.getText()));
            Cliente cliente = new Cliente();
            cliente.setTransaccion(t);
            new Thread(cliente).start();
            field97.setText("");
        }
        
    }
    
    private Transaccion crear(String tipoCombustible,int litros)
    {
        Timestamp time = null;
        String idTransaccion = "11";
        int precioPorLitro= 500;
        int total=litros*precioPorLitro;
        
        Transaccion t = new Transaccion(time,idTransaccion,tipoCombustible,litros,precioPorLitro,total);
        return t;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

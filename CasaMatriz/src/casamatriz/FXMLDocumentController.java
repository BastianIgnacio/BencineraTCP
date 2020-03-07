/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import java.net.URL;
import java.util.ResourceBundle;
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
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonAction(ActionEvent event) {
        
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
    
}

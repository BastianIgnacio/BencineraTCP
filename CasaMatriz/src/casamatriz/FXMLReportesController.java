/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import casamatriz.servidor.Worker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author macbook
 */
public class FXMLReportesController implements Initializable {
    @FXML
    private Button generar;
    @FXML
    private WebView webView;
    @FXML
    private ChoiceBox<Worker> sucursalesBox;
    ArrayList<Worker> sucursales;
    
    public FXMLReportesController(){
        this.sucursales = new ArrayList<>();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursalesBox.getItems().setAll(sucursales);
        sucursalesBox.setConverter(new StringConverter<Worker>() {
            @Override
            public String toString(Worker uni) {
                return uni.getNombre();
            }
            @Override
            // not used...
            public Worker fromString(String s) {
                return null ;
            }
        });
        
    }    

    public void setSucursales(ArrayList<Worker> sucursales){
        this.sucursales = sucursales;
        sucursalesBox.getItems().clear();
        sucursalesBox.getItems().setAll(sucursales);
        System.out.println("se pasaron");
    }
    
    @FXML
    private void buttonAction(ActionEvent event) {
        if(event.getSource() == generar){
            Worker suc = sucursalesBox.getSelectionModel().getSelectedItem();
            if(suc!=null){
                WebEngine engine = webView.getEngine();
                engine.loadContent("<h3 style=\"text-align:center\">Reporte de sucursal</h3>\n" +
    "<table align=\"center\" stlye=\";idth:100%\">\n" +
    "  <thead>\n" +
    "    <tr>\n" +
    "      <th>Tipo combustible</th>\n" +
    "      <th>Total de cargas</th>\n" +
    "      <th>Litros consumidos</th>\n" +
    "      <th>Total de venta</th>\n" +
    "    </tr>\n" +
    "  </thead>\n" +
    "  <tbody>\n" +
    "    <tr>\n" +
    "      <td>93</td>\n" +
    "      <td>52</td>\n" +
    "      <td>1123</td>\n" +
    "      <td>890.000</td>\n" +
    "    </tr>\n" +
    "  </tbody>\n" +
    "</table>\n" +
    "<h4 style=\"text-align:right\"><b>Total de ventas:</b> <span style=\"font-weight: normal\">$21.342.134</span></h4>");
            }
        }
    }
    
}

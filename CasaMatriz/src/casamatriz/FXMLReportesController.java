/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import casamatriz.servidor.BaseDatos;
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
import sucursal.Reporte;
import sucursal.Sucursal;

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
    private ChoiceBox<Sucursal> sucursalesBox;
    ArrayList<Sucursal> sucursales;
    BaseDatos bd;
    
    public FXMLReportesController(){
        this.bd = BaseDatos.crearInstancia();

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.sucursales = this.bd.getSucursales();
        sucursalesBox.getItems().setAll(sucursales);
        sucursalesBox.setConverter(new StringConverter<Sucursal>() {
            @Override
            public String toString(Sucursal uni) {
                return uni.getNombre();
            }
            @Override
            // not used...
            public Sucursal fromString(String s) {
                return null ;
            }
        });
        
    }    
    
    @FXML
    private void buttonAction(ActionEvent event) {
        if(event.getSource() == generar){
            long ventas = 0;
            Sucursal suc = sucursalesBox.getSelectionModel().getSelectedItem();
            if(suc!=null){
                ArrayList<Reporte> reportes = this.bd.getReporteSucursal((int)suc.getId());
                WebEngine engine = webView.getEngine();
                String html = "<h3 style=\"text-align:center\">Reporte de sucursal</h3>\n" +
    "<table align=\"center\" stlye=\";idth:100%\">\n" +
    "  <thead>\n" +
    "    <tr>\n" +
    "      <th>Tipo combustible</th>\n" +
    "      <th>Total de cargas</th>\n" +
    "      <th>Litros consumidos</th>\n" +
    "      <th>Total de venta</th>\n" +
    "    </tr>\n" +
    "  </thead>\n" +
    "  <tbody>\n"; 
                for (Reporte reporte : reportes) {
                    html += "<tr><td>"+reporte.getTipoCombustbiel()+"</td><td>"+reporte.getCargas()+"</td><td>"+reporte.getLitros()+"</td><td>"+reporte.getVentas()+"</td>";
                    ventas +=reporte.getVentas();
                }
    html += "</tbody>\n" +
    "</table>\n" +
    "<h4 style=\"text-align:right\"><b>Total de ventas:</b> <span style=\"font-weight: normal\">"+ventas+"</span></h4>";
            engine.loadContent(html);
            }
        }
    }
    
}

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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;
import sucursal.Falla;
import sucursal.Informacion;
import sucursal.Transaccion;

/**
 *
 * @author roduc
 */
public class FXMLDocumentController implements Initializable {
    
    
    
    private TextField field93;
    private TextField field95;
    private TextField field97;
    private TextField fieldDiesel;
    private TextField fieldKerosene;
    private Button boton93;
    private Button boton95;
    private Button boton97;
    private Button botonDiesel;
    private Button botonKerosene;
    @FXML
    private ComboBox<String> comboBoxTipo;
    @FXML
    private TextField textFieldLitros;
    @FXML
    private Button cargarButton;
    @FXML
    private Button botonConexion;
    @FXML
    private TableView<Transaccion> tbUltimasCargas;
    @FXML
    private TableColumn<Transaccion, Timestamp> tc_uc_fecha;
    @FXML
    private TableColumn<Transaccion, String> tc_uc_tipo;
    @FXML
    private TableColumn<Transaccion, Integer> tc_uc_litros;
    @FXML
    private TableColumn<Transaccion, Integer> tc_uc_precio_litro;
    @FXML
    private TableColumn<Transaccion, Integer> tc_uc_total;
    
    private Cliente cliente;
    @FXML
    private Label titulo;
    @FXML
    private TableView<Falla> tbFallas;
    @FXML
    private TableColumn<Falla, Timestamp> tc_fcaida;
    @FXML
    private TableColumn<Falla, Timestamp> tc_freconexion;
    @FXML
    private TableColumn<Falla, Long> tc_tcaida;
    ArrayList<Transaccion> trans = new ArrayList();
    
    
    
    @FXML
    private void buttonAction(ActionEvent event) throws ClassNotFoundException, IOException {
        
        if(event.getSource()==this.cargarButton)
        {
            System.out.println(this.comboBoxTipo.getValue());
            Transaccion t = this.crear(this.comboBoxTipo.getValue(),parseInt(textFieldLitros.getText()));
            cliente.enviarTransaccion(t);
            this.insertarTransaccionTabla(t);
        }
        if(event.getSource()==this.boton93)
        {
            System.out.println(field93.getText());
            Transaccion t = this.crear("93",parseInt(field93.getText()));
            cliente.enviarTransaccion(t);
            field93.setText("");
        }
        
        if(event.getSource()==this.boton95)
        {
            System.out.println(field95.getText());
            Transaccion t = this.crear("95",parseInt(field95.getText()));
            cliente.enviarTransaccion(t);
            field95.setText("");
        }
        
        if(event.getSource()==this.boton97)
        {
            System.out.println(field97.getText());
            Transaccion t = this.crear("97",parseInt(field97.getText()));
            cliente.enviarTransaccion(t);
            field97.setText("");
        }
        
        if(event.getSource()==this.botonDiesel)
        {
            System.out.println(fieldDiesel.getText());
            Transaccion t = this.crear("diesel",parseInt(fieldDiesel.getText()));
            
            cliente.enviarTransaccion(t);
            fieldDiesel.setText("");
        }
        
         if(event.getSource()==this.botonKerosene)
        {
            System.out.println(fieldKerosene.getText());
            Transaccion t = this.crear("kerosene",parseInt(fieldKerosene.getText()));
            cliente.enviarTransaccion(t);
            fieldKerosene.setText("");
        }
        
    }
    
    private Transaccion crear(String tipoCombustible,int litros)
    {
        Timestamp time = new Timestamp(new java.util.Date().getTime());
        int precioPorLitro= 0;
        switch(tipoCombustible){
            case "93":
                precioPorLitro = SharedInfo.info.getBencina93();
                break;
            case "95":
                precioPorLitro = SharedInfo.info.getBencina95();
                break;
            case "97":
                precioPorLitro = SharedInfo.info.getBencina97();
                break;
            case "kerosene":
                precioPorLitro = SharedInfo.info.getKerosene();
                break;
            case "diesel":
                precioPorLitro = SharedInfo.info.getDiesel();
                break;
        }
        int total=litros*precioPorLitro;

        Transaccion t = new Transaccion(time,tipoCombustible,litros,precioPorLitro,total, SharedInfo.idSurtidor);
        return t;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.titulo.setText("Surtidor #"+SharedInfo.idSurtidor);
         this.cliente = new Cliente();
         cliente.setControler(this);
         SharedInfo.info = new Informacion(0,0,0,0,0);
         new Thread(cliente).start();
        
        this.comboBoxTipo.getItems().add("kerosene");
        this.comboBoxTipo.getItems().add("diesel");
        this.comboBoxTipo.getItems().add("93");
        this.comboBoxTipo.getItems().add("95");
        this.comboBoxTipo.getItems().add("97");
        
        this.tc_uc_fecha.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.tc_uc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        this.tc_uc_litros.setCellValueFactory(new PropertyValueFactory<>("litros"));
        this.tc_uc_precio_litro.setCellValueFactory(new PropertyValueFactory<>("precioPorLitro"));
        this.tc_uc_total.setCellValueFactory(new PropertyValueFactory<>("total"));
       
        this.tc_fcaida.setCellValueFactory(new PropertyValueFactory<>("caida"));
        this.tc_freconexion.setCellValueFactory(new PropertyValueFactory<>("reconexion"));
        this.tc_tcaida.setCellValueFactory(new PropertyValueFactory<>("tiempo_caido"));
        
        
        
        
        
        
      
        
       
       // Filto para que en texfield solo acepte numeros  
       UnaryOperator<Change> integerFilter = change -> {
       String newText = change.getControlNewText();
       if (newText.matches("-?([1-9][0-9]*)?")) { 
           return change;
       }
       return null;
       };

       this.textFieldLitros.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
    }    
    
    public void insertarTransaccionTabla(Transaccion t){
         trans.add(t);
        this.updateUltimasTransacciones(trans);
       
    }
    
    public void updateUltimasTransacciones(ArrayList<Transaccion> transacciones)
    {
        this.tbUltimasCargas.getItems().clear();
        ObservableList<Transaccion> data = FXCollections.observableArrayList(transacciones);
        this.tbUltimasCargas.setItems(data);
    }
    
    public void updateFallas(ArrayList<Falla> fallas)
    {
        this.tbFallas.getItems().clear();
        ObservableList<Falla> data = FXCollections.observableArrayList(fallas);
        this.tbFallas.setItems(data);
        System.out.println(fallas);
    }
    
    public void comprobarConexion(boolean isConnected){
        if (!isConnected == true){
            cargarButton.setDisable(true);
            //botonConexion.setText("Desconectado");
            botonConexion.setStyle("-fx-base: red;");
            
        }
        else{
            //botonConexion.setText("Conectado");
            cargarButton.setDisable(false);
            botonConexion.setStyle("-fx-base: green;");
            
        }
        
    }
    
    
    
}

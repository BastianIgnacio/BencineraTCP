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
import javafx.scene.control.cell.PropertyValueFactory;
import sucursal.Transaccion;

/**
 *
 * @author roduc
 */
public class FXMLDocumentController implements Initializable {
    
    private int ID_SURTIDOR = 2;
    
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
    @FXML
    private TableView<Transaccion> tbCargasEnCola;
    @FXML
    private TableColumn<Transaccion, Timestamp> tc_cc_fecha;
    @FXML
    private TableColumn<Transaccion, String>  tc_cc_tipo;
    @FXML
    private TableColumn<Transaccion, Integer> tc_cc_litros;
    @FXML
    private TableColumn<Transaccion, Integer>  tc_cc_precio_litro;
    @FXML
    private TableColumn<Transaccion, Integer> tc_cc_total;
    
    @FXML
    private void buttonAction(ActionEvent event) throws ClassNotFoundException {
        
        if(event.getSource()==this.cargarButton)
        {
            System.out.println("Cargar");
        }
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
        
        if(event.getSource()==this.botonDiesel)
        {
            System.out.println(fieldDiesel.getText());
            Transaccion t = this.crear("diesel",parseInt(fieldDiesel.getText()));
            Cliente cliente = new Cliente();
            cliente.setTransaccion(t);
            new Thread(cliente).start();
            fieldDiesel.setText("");
        }
        
         if(event.getSource()==this.botonKerosene)
        {
            System.out.println(fieldKerosene.getText());
            Transaccion t = this.crear("kerosene",parseInt(fieldKerosene.getText()));
            Cliente cliente = new Cliente();
            cliente.setTransaccion(t);
            new Thread(cliente).start();
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

        Transaccion t = new Transaccion(time,tipoCombustible,litros,precioPorLitro,total, ID_SURTIDOR);
        return t;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        this.comboBoxTipo.getItems().add("Kerosene");
        this.comboBoxTipo.getItems().add("Diesel");
        this.comboBoxTipo.getItems().add("93");
        this.comboBoxTipo.getItems().add("95");
        this.comboBoxTipo.getItems().add("97");
        
        this.tc_uc_fecha.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.tc_uc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        this.tc_uc_litros.setCellValueFactory(new PropertyValueFactory<>("litros"));
        this.tc_uc_precio_litro.setCellValueFactory(new PropertyValueFactory<>("precioPorLitro"));
        this.tc_uc_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        
        this.tc_cc_fecha.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.tc_cc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        this.tc_cc_litros.setCellValueFactory(new PropertyValueFactory<>("litros"));
        this.tc_cc_precio_litro.setCellValueFactory(new PropertyValueFactory<>("precioPorLitro"));
        this.tc_cc_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        ArrayList<Transaccion> trans = new ArrayList();
        
        Timestamp time = new Timestamp(new java.util.Date().getTime());
        Transaccion t = new Transaccion(time,"97",2,24,444,2);
        
        trans.add(t);
        
        
        time = new Timestamp(new java.util.Date().getTime());
        t = new Transaccion(time,"95",2,24,444,2);
        
        trans.add(t);
        
        this.updateUltimasTransacciones(trans);
        this.updatseCargasEnCola(trans);
        
        
    }    
    
    
    public void updateUltimasTransacciones(ArrayList<Transaccion> transacciones)
    {
        this.tbUltimasCargas.getItems().clear();
        ObservableList<Transaccion> data = FXCollections.observableArrayList(transacciones);
        this.tbUltimasCargas.setItems(data);
    }
    
    public void updatseCargasEnCola(ArrayList<Transaccion> transacciones)
    {
        this.tbCargasEnCola.getItems().clear();
        
        ObservableList<Transaccion> data = FXCollections.observableArrayList(transacciones);
        this.tbCargasEnCola.setItems(data);
    }
    
    
    
}

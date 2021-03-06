/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sucursal;


import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author macbook
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    private Button transacciones;
    @FXML
    private Button ganancias;
    @FXML
    private Button precios;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn<Transaccion,Timestamp> tcfecha;
    @FXML
    private TableColumn<Transaccion,String> tctipo;
    @FXML
    private TableColumn<Transaccion,Integer> tclitros;
    @FXML
    private TableColumn<Transaccion,Integer> tcprecioporlitro;
    @FXML
    private TableColumn<Transaccion,Integer> tctotal;
    @FXML
    private TableColumn<Transaccion,String> tcsurtidor;
    @FXML
    private Button botonConexion;
    Servidor server;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cliente cliente = new Cliente();
        cliente.setControler(this);
        Thread c = new Thread(cliente);
        c.start();
        this.iniciarServidor();
        
        BaseDeDatos bdatos = BaseDeDatos.crearInstancia();
        ArrayList<Transaccion> bdtrans = bdatos.getTransaccionesArray();

        this.tcfecha.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.tctipo.setCellValueFactory(new PropertyValueFactory<>("tipoCombustible"));
        this.tclitros.setCellValueFactory(new PropertyValueFactory<>("litros"));
        this.tcprecioporlitro.setCellValueFactory(new PropertyValueFactory<>("precioPorLitro"));
        this.tctotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.tcsurtidor.setCellValueFactory(new PropertyValueFactory<>("refSurtidor"));
        updateTransacciones(bdtrans);
        
        
    }    

    @FXML
    private void buttonAction(ActionEvent event) {
            System.out.println("Cantidad de litros usados 93 => " + InfoSurtidor.cantidad93);
            System.out.println("Cantidad de cargas 93 => " + InfoSurtidor.cargas93);
            System.out.println("Cantidad de litros usados 95 => " + InfoSurtidor.cantidad95);
            System.out.println("Cantidad de cargas 95 => " + InfoSurtidor.cargas95);
             System.out.println("Cantidad de litros usados 97 => " + InfoSurtidor.cantidad97);
            System.out.println("Cantidad de cargas 97 => " + InfoSurtidor.cargas97);
             System.out.println("Cantidad de litros usados Diesel => " + InfoSurtidor.cantidadDiesel);
            System.out.println("Cantidad de cargas Diesel => " + InfoSurtidor.cargasDiesel);
             System.out.println("Cantidad de litros usados Kerosene => " + InfoSurtidor.cantidadKerosene);
            System.out.println("Cantidad de cargas Kerosene => " + InfoSurtidor.cargasKerosene);
            
        if(event.getSource()==this.ganancias)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación margen de ganancias");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el margen de ganancias:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int margen = Integer.parseInt(result.get());
                InfoSurtidor.margen = margen;
                
            }
            System.out.println(InfoSurtidor.margen);
        }
        if(event.getSource()==this.precios)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Precios de combustibles");
            alert.setHeaderText(null);
            alert.setContentText("Precio 93: " + InfoSurtidor.info.getBencina93() + "\nPrecio 95: " + InfoSurtidor.info.getBencina95() + "\nPrecio 97:" + InfoSurtidor.info.getBencina97() + "\nDiesel: "+ InfoSurtidor.info.getDiesel() + "\nKerosene: " + InfoSurtidor.info.getKerosene());
            alert.showAndWait();
        }
    }
    
    private void iniciarServidor()
    {
        server = new Servidor();
        server.setControlador(this);
        Thread a = new Thread(server);
        a.start();
    }
    
    
    public void enviarPrecios(){
        for (Worker surtidor : this.server.getSurtidores()) {
            surtidor.enviar();
        }
    }
    
  
    
    public void updateTransacciones(ArrayList<Transaccion> transacciones)
    {
        this.tablaTransacciones.setEditable(true);
        System.out.println("Updateeeeeeee");
        this.tablaTransacciones.getItems().clear();
        System.out.println(" tamano " + String.valueOf(transacciones.size()));
        
        ObservableList<Transaccion> data = FXCollections.observableArrayList(transacciones);
        this.tablaTransacciones.setItems(data);
        
    
    }
    
    public void comprobarConexion(boolean isConnected){
        if (!isConnected == true){
           
            //botonConexion.setText("Desconectado");
            botonConexion.setStyle("-fx-base: red;");
            
        }
        else{
            //botonConexion.setText("Conectado");
         
            botonConexion.setStyle("-fx-base: green;");
            
        }
        
    }
}

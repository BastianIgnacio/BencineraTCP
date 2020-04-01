/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casamatriz;

import casamatriz.servidor.Servidor;
import casamatriz.servidor.Worker;
import sucursal.Transaccion;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sucursal.Informacion;

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
    @FXML
    private TableView<Worker> tvSucursales;
    @FXML
    private TableColumn<Worker, String> tcSucurcalNombre;
    @FXML
    private TableColumn<Worker, String>  tcSucursalDireccionIp;
    @FXML
    private TableColumn<Worker, String> tcSucursalEstado;
    @FXML
    private TableView<?> tvModificaciones;
    @FXML
    private TableColumn<?, ?> tcModificacionTipo;
    @FXML
    private TableColumn<?, ?> tcModificacionPrecio;
    @FXML
    private TableColumn<?, ?> tcModificacionFecha;
    @FXML
    private Button reportes;
    
    Servidor servidor;
    Thread hiloServer;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        servidor = new Servidor(this);
        hiloServer = new Thread(servidor);
        hiloServer.start();
        
        iniciarTablesView();
    }

    private void iniciarTablesView()
    {
         this.tcSucurcalNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
         this.tcSucursalDireccionIp.setCellValueFactory(new PropertyValueFactory<>("address"));
         this.tcSucursalEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    
    }
    
    
    public void cerrarServidor(){
        this.servidor.cerrar();
        this.hiloServer.interrupt();
    }
    
    @FXML
    private void buttonAction(ActionEvent event) throws ClassNotFoundException, IOException {
        
        if(event.getSource()==this.reportes)
        {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLReportes.fxml"));
               BorderPane root1 = (BorderPane) fxmlLoader.load();
               Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
               //stage.initStyle(StageStyle.UNDECORATED);
               stage.setTitle("Generador de reportes");
               stage.setScene(new Scene(root1));  
               stage.show();
            
        }
        
        if(event.getSource()==this.establecer_cliente)
        {
            
        }
        if(event.getSource()==this.preciosActuales){
            System.out.println(SharedInfo.info);
        }
        if(event.getSource()==this.modificar_93)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 93 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 93 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 93 => " + precio);
                SharedInfo.info.setBencina93(precio);
                FileHandler.saveInfo();
                for (Worker sucursal : servidor.getSucursales()) {
                    sucursal.enviar();
                }
            }
        }
        if(event.getSource()==this.modificar_95)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 95 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 95 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 95 => " + precio);
                SharedInfo.info.setBencina95(precio);
                FileHandler.saveInfo();
                for (Worker sucursal : servidor.getSucursales()) {
                    sucursal.enviar();
                }
            }
        
        }
        if(event.getSource()==this.modificar_97)
        {
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de bencina 97 octanos");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para la bencina de 97 octanos:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio bencina 97 => " + precio);
                SharedInfo.info.setBencina97(precio);
                FileHandler.saveInfo();
                for (Worker sucursal : servidor.getSucursales()) {
                    sucursal.enviar();
                }
            }
        }
        if(event.getSource()==this.modificar_kerosene)
        {
        
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de kerosene");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para el kerosene:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio kerosene => " + precio);
                SharedInfo.info.setKerosene(precio);
                FileHandler.saveInfo();
                for (Worker sucursal : servidor.getSucursales()) {
                    sucursal.enviar();
                }
            }
        }
        if(event.getSource()==this.modificar_diesel)
        {
        
              TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificación precio de diesel");
            dialog.setHeaderText("Complete la información");
            dialog.setContentText("Ingrese el nuevo precio para el diesel es:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                int precio = Integer.parseInt(result.get());
                System.out.println("INFO: Nuevo precio diesel=> " + precio);
                SharedInfo.info.setDiesel(precio);
                FileHandler.saveInfo();
                for (Worker sucursal : servidor.getSucursales()) {
                    sucursal.enviar();
                }
            }
        }
    }
    
    public void updateSucursales(ArrayList<Worker> array)
    {
        this.tvSucursales.getItems().clear();
        ObservableList<Worker> data = FXCollections.observableArrayList(array);
        this.tvSucursales.setItems(data);
    }

}

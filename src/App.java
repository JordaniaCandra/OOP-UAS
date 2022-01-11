import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    Stage windowStage;
    TableView<baju> table;
    TableView<baju> tableView = new TableView<baju>();
    TextField idInput, merek_bajuInput, ukuranInput, kode_bajuInput, hargaInput;

    @Override
    public void start(Stage stage) {

        // Menampilkan nama window
        windowStage = stage;
        windowStage.setTitle("DataBase - Baju");
       
        //Menampilkan tabel
        TableColumn<baju, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<baju, String> columnMerek_Baju = new TableColumn<>("Merek Baju");
        columnMerek_Baju.setCellValueFactory(new PropertyValueFactory<>("merek_baju"));

        TableColumn<baju, String> columnUkuran = new TableColumn<>("Ukuran");
        columnUkuran.setCellValueFactory(new PropertyValueFactory<>("ukuran"));

        TableColumn<baju, String> columnKode_Baju = new TableColumn<>("Kode Baju");
        columnKode_Baju.setCellValueFactory(new PropertyValueFactory<>("kode_baju"));

        TableColumn<baju, String> columnHarga = new TableColumn<>("Harga");
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnMerek_Baju);
        tableView.getColumns().add(columnUkuran);
        tableView.getColumns().add(columnKode_Baju);
        tableView.getColumns().add(columnHarga);

        //Input id
        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        //Input nama barang
        merek_bajuInput = new TextField();
        merek_bajuInput.setPromptText("Merek baju");
        merek_bajuInput.setMinWidth(20);

        //Input merk barang
        ukuranInput = new TextField();
        ukuranInput.setPromptText("Ukuran");
        ukuranInput.setMinWidth(20);

        //Input seri barang
        kode_bajuInput = new TextField();
        kode_bajuInput.setPromptText("Kode baju");
        kode_bajuInput.setMinWidth(20);

        //Input harga
        hargaInput = new TextField();
        hargaInput.setPromptText("Harga");
        hargaInput.setMinWidth(20);

        //Button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());
    
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, merek_bajuInput, ukuranInput, kode_bajuInput, hargaInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/db_baju";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from tb_baju");

            while (record.next()) {
                tableView.getItems().add(new baju(record.getInt("id"), record.getString("merek_baju"), record.getString("ukuran"), record.getString("kode_baju"), record.getString("harga")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }




    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into tb_baju SET merek_baju='%s', ukuran='%s', kode_baju='%s', harga='%s'";
                    sql = String.format(sql, merek_bajuInput.getText(), ukuranInput.getText(), kode_bajuInput.getText(), hargaInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    merek_bajuInput.clear();
                    ukuranInput.clear();
                    kode_bajuInput.clear();
                    hargaInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };


    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update tb_baju set merek_baju = '%s', ukuran = '%s' ,  harga = '%s' WHERE kode_baju ='%s'";
            sql = String.format(sql, merek_bajuInput.getText(), ukuranInput.getText(), hargaInput.getText(),kode_bajuInput.getText());
            state.execute(sql);
            merek_bajuInput.clear();
            ukuranInput.clear();
            hargaInput.clear();
            kode_bajuInput.clear();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from tb_baju where kode_baju ='%s';";
            sql = String.format(sql, kode_bajuInput.getText());
            state.execute(sql);
            kode_bajuInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_baju");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new baju(rs.getInt("id"), rs.getString("merek_baju"), rs.getString("ukuran"), rs.getString("kode_baju"), rs.getString("harga")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}
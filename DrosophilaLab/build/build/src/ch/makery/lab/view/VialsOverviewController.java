package ch.makery.lab.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ch.makery.lab.MainApp;
import ch.makery.lab.model.Kolba;

public class VialsOverviewController {
    @FXML
    private TableView<Kolba> KolbaTable;
    @FXML
    private TableColumn<Kolba, Number> numerKolbyColumn;
    @FXML
    private TableColumn<Kolba, String> szczepColumn;
    @FXML
    private TableColumn<Kolba, LocalDate> dataZalozeniaColumn;
   
    @FXML
    private Label numerKolbyLabel;
    @FXML
    private Label szczepLabel;
    @FXML
    private Label dataZalozeniaLabel;
    @FXML
    private Label rodziceLabel;
    @FXML
    private Label dziewiceLabel;
    @FXML
    private Label samceLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public VialsOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Kolba table with the two columns.
        numerKolbyColumn.setCellValueFactory(cellData -> cellData.getValue().NumerKolbyProperty());
        szczepColumn.setCellValueFactory(cellData -> cellData.getValue().SzczepProperty());
        dataZalozeniaColumn.setCellValueFactory(cellData -> cellData.getValue().dataZalozeniaProperty());
        
     // Clear person details.
        showKolbaDetails(null);

        // Listen for selection changes and show the person details when changed.
        KolbaTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showKolbaDetails(newValue));
    }
    /**
     * Fills all text fields to show details about the kolba.
     * If the specified kolba is null, all text fields are cleared.
     * 
     * @param kolba the kolba or null
     */
    private void showKolbaDetails(Kolba kolba) {
        if (kolba != null) {
            // Fill the labels with info from the kolba object.
            numerKolbyLabel.setText(Integer.toString(kolba.getNumerKolby()));
            szczepLabel.setText(kolba.getSzczep());
            dataZalozeniaLabel.setText(kolba.getDataZalozenia().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            rodziceLabel.setText(Boolean.toString(kolba.getRodzice()));
            dziewiceLabel.setText(Boolean.toString(kolba.getDziewice()));
            samceLabel.setText(Boolean.toString(kolba.getSamce()));

            // TODO: We need a way to convert the samce into a String! 
            // samceLabel.setText(...);
        } else {
            // kolba is null, remove all the text.
            numerKolbyLabel.setText("");
            szczepLabel.setText("");
            dataZalozeniaLabel.setText("");
            rodziceLabel.setText("");
            dziewiceLabel.setText("");
            samceLabel.setText("");
        }
    }
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewKolba() {
        Kolba tempKolba = new Kolba();
        boolean okClicked = mainApp.showKolbaEditDialog(tempKolba);
        if (okClicked) {
            mainApp.getKolbaData().add(tempKolba);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditKolba() {
        Kolba selectedKolba = KolbaTable.getSelectionModel().getSelectedItem();
        if (selectedKolba != null) {
            boolean okClicked = mainApp.showKolbaEditDialog(selectedKolba);
            if (okClicked) {
                showKolbaDetails(selectedKolba);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wskazania");
            alert.setHeaderText("Nie wybrano kolby");
            alert.setContentText("Proszê wskazaæ kolbê!");

            alert.showAndWait();
        }
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        KolbaTable.setItems(mainApp.getKolbaData());
    }
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteKolba() {
        int selectedIndex = KolbaTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            KolbaTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wyboru");
            alert.setHeaderText("Nie wybrano kolby");
            alert.setContentText("Proszê wskazaæ kolbê do usuniêcia.");

            alert.showAndWait();
        }
    }
}
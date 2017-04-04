package ch.makery.lab.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.lab.model.Kolba;
import ch.makery.lab.util.DateUtil;

/**
 * Dialog to edit details of a kolba.
 * 
 * @author KusyMat
 */
public class KolbaEditDialogController {

	 @FXML
	    private TextField numerKolbyField;
	    @FXML
	    private TextField szczepField;
	    @FXML
	    private TextField dataZalozeniaField;
	    @FXML
	    private TextField rodziceField;
	    @FXML
	    private TextField dziewiceField;
	    @FXML
	    private TextField samceField;


    private Stage dialogStage;
    private Kolba kolba;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the kolba to be edited in the dialog.
     * 
     * @param kolba
     */
    public void setKolba(Kolba kolba) {
        this.kolba = kolba;

        numerKolbyField.setText(Integer.toString(kolba.getNumerKolby()));
        szczepField.setText(kolba.getSzczep());
        dataZalozeniaField.setText(DateUtil.format(kolba.getDataZalozenia()));
        rodziceField.setText(Boolean.toString(kolba.getRodzice()));
        dziewiceField.setText(Boolean.toString(kolba.getDziewice()));
        samceField.setText(Boolean.toString(kolba.getSamce()));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            kolba.setNumerKolby(Integer.parseInt(numerKolbyField.getText()));
            kolba.setSzczep(szczepField.getText());
            kolba.setDataZalozenia(DateUtil.parse(dataZalozeniaField.getText()));
            kolba.setRodzice(Boolean.parseBoolean (rodziceField.getText()));
            kolba.setDziewice(Boolean.parseBoolean(dziewiceField.getText()));
            kolba.setSamce(Boolean.parseBoolean(samceField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (numerKolbyField.getText() == null || numerKolbyField.getText().length() == 0) {
            errorMessage += "B³êdny numer kolby!\n"; 
        }else {
            
            try {
                Integer.parseInt(numerKolbyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Numer kolby musi byæ numerem!\n"; 
            }
        }
        if (szczepField.getText() == null || szczepField.getText().length() == 0) {
            errorMessage += "B³êdny szczep!\n"; 
        }
        if (dataZalozeniaField.getText() == null || dataZalozeniaField.getText().length() == 0) {
            errorMessage += "B³êdna data za³o¿enia!\n"; 
        } else {
            if (!DateUtil.validDate(dataZalozeniaField.getText())) {
                errorMessage += "B³êdna data! Zapisz j¹ jako dd.mm.yyyy!\n";
            }
        }

        if (rodziceField.getText() == null || rodziceField.getText().length() == 0) {
            errorMessage += "B³êdne okreœlenie!\n"; 
        } 
            


        if (dziewiceField.getText() == null || dziewiceField.getText().length() == 0) {
            errorMessage += "B³êdne okreœlenie!\n"; 
        }

        if (samceField.getText() == null || samceField.getText().length() == 0) {
            errorMessage += "B³êdne okreœlenie!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Z³e pola");
            alert.setHeaderText("Proszê poprawiæ b³êdy");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
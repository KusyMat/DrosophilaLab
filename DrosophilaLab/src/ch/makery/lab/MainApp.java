package ch.makery.lab;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.makery.lab.model.Kolba;
import ch.makery.lab.model.KolbaListWrapper;
import ch.makery.lab.view.KolbaEditDialogController;
import ch.makery.lab.view.RootLayoutController;
import ch.makery.lab.view.VialsOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	// ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of kolbas.
     */
    private ObservableList<Kolba> kolbaData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        kolbaData.add(new Kolba(1, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(2, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(3, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(4, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(5, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(6, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(7, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(8, "Canton S", LocalDate.of(1999, 2, 21)));
        kolbaData.add(new Kolba(9, "Canton S", LocalDate.of(1999, 2, 21)));
    }

    /**
     * Returns the data as an observable list of kolbas. 
     * @return
     */
    public ObservableList<Kolba> getKolbaData() {
        return kolbaData;
    }

    // ... THE REST OF THE CLASS ...
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DrosophilaLab");

        this.primaryStage.getIcons().add(new Image("file:resources/images/1491306741_insects-02.png"));
        
        initRootLayout();

        showVialsOverview();
    }
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getKolbaFilePath();
        if (file != null) {
            loadKolbaDataFromFile(file);
        }
    }

    /**
     * Shows the vials overview inside the root layout.
     */
    public void showVialsOverview() {
    	 try {
    	        // Load vials overview.
    	        FXMLLoader loader = new FXMLLoader();
    	        loader.setLocation(MainApp.class.getResource("view/VialsOverview.fxml"));
    	        AnchorPane vialsOverview = (AnchorPane) loader.load();

    	        // Set vials overview into the center of root layout.
    	        rootLayout.setCenter(vialsOverview);

    	        // Give the controller access to the main app.
    	        VialsOverviewController controller = loader.getController();
    	        controller.setMainApp(this);

    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showKolbaEditDialog(Kolba kolba) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/KolbaEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edytuj dane kolby");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            KolbaEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setKolba(kolba);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getKolbaFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setKolbaFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("DrosophilaLab - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("DrosophilaLab");
        }
    }
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public void loadKolbaDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(KolbaListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            KolbaListWrapper wrapper = (KolbaListWrapper) um.unmarshal(file);

            kolbaData.clear();
            kolbaData.addAll(wrapper.getKolby());

            // Save the file path to the registry.
            setKolbaFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("B³¹d");
            alert.setHeaderText("Nie mogê wczytaæ pliku!");
            alert.setContentText("Nie mog³em wczytaæ danych z pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    public void saveKolbaDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(KolbaListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            KolbaListWrapper wrapper = new KolbaListWrapper();
            wrapper.setKolby(kolbaData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setKolbaFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("B³¹d");
            alert.setHeaderText("Nie mo¿na zapisaæ pliku");
            alert.setContentText("Nie mo¿na zapisaæ danych do pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
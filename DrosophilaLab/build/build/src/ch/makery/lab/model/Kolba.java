package ch.makery.lab.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.makery.lab.util.LocalDateAdapter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Kusymat
 */
public class Kolba {

    private final IntegerProperty numerKolby;
    private final StringProperty szczep;
    private final ObjectProperty<LocalDate> dataZalozenia;
    private final BooleanProperty rodzice;
    private final BooleanProperty dziewice;
    private final BooleanProperty samce;
   

    /**
     * Default constructor.
     */
    public Kolba() {
        this(0, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param numerKolby
     * @param szczep
     */
    public Kolba(Integer numerKolby, String szczep, LocalDate dataZalozenia) {
        this.numerKolby = new SimpleIntegerProperty(numerKolby);
        this.szczep = new SimpleStringProperty(szczep);

        // Some initial dummy data, just for convenient testing.
        this.dataZalozenia = new SimpleObjectProperty<LocalDate>(dataZalozenia);
        this.rodzice = new SimpleBooleanProperty(false);
        this.dziewice = new SimpleBooleanProperty(false);
        this.samce= new SimpleBooleanProperty(false);
    }

    public Integer getNumerKolby() {
        return numerKolby.get();
    }

    public void setNumerKolby(Integer numerKolby) {
        this.numerKolby.set(numerKolby);
    }

    public IntegerProperty NumerKolbyProperty() {
        return numerKolby;
    }

    public String getSzczep() {
        return szczep.get();
    }

    public void setSzczep(String szczep) {
        this.szczep.set(szczep);
    }

    public StringProperty SzczepProperty() {
        return szczep;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDataZalozenia() {
        return dataZalozenia.get();
    }
    

    public void setDataZalozenia(LocalDate dataZalozenia) {
        this.dataZalozenia.set(dataZalozenia);
    }

    public ObjectProperty<LocalDate> dataZalozeniaProperty() {
        return dataZalozenia;
    }

    public boolean getRodzice() {
        return rodzice.get();
    }

    public void setRodzice(boolean rodzice) {
        this.rodzice.set(rodzice);
    }

    public BooleanProperty rodziceProperty() {
        return rodzice;
    }

    public boolean getDziewice() {
        return dziewice.get();
    }

    public void setDziewice(boolean dziewice) {
        this.dziewice.set(dziewice);
    }

    public BooleanProperty dziewiceProperty() {
        return dziewice;
    }

    public boolean getSamce() {
        return samce.get();
    }

    public void setSamce(boolean samce) {
        this.samce.set(samce);
    }

    public BooleanProperty samceProperty() {
        return samce;
    }
}
package ch.makery.lab.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author KusyMat
 */
@XmlRootElement(name = "kolby")
public class KolbaListWrapper {

    private List<Kolba> kolby;

    @XmlElement(name = "kolba")
    public List<Kolba> getKolby() {
        return kolby;
    }

    public void setKolby(List<Kolba> kolby) {
        this.kolby = kolby;
    }
}
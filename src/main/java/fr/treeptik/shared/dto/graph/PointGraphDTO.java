package fr.treeptik.shared.dto.graph;

import java.io.Serializable;
import java.util.Date;

public class PointGraphDTO implements Serializable {

    private Date date;
    private Float valeur;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getValeur() {
        return valeur;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Point [date=" + date + ", valeur=" + valeur + "]";
    }
}

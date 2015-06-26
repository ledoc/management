package fr.treeptik.model;

import java.io.Serializable;
import java.util.Date;

public class PointGraphDTO implements Serializable {

    private Date date;
    private Double valeur;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Point [date=" + date + ", valeur=" + valeur + "]";
    }
}
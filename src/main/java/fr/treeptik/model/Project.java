package fr.treeptik.model;

import java.util.List;

public class Project {

    private List<Site> sites;
    private List<Ouvrage> ouvrages;
    private List<Enregistreur> enregistreurs;
    private List<Mesure> mesures;

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public List<Ouvrage> getOuvrages() {
        return ouvrages;
    }

    public void setOuvrages(List<Ouvrage> ouvrages) {
        this.ouvrages = ouvrages;
    }

    public List<Enregistreur> getEnregistreurs() {
        return enregistreurs;
    }

    public void setEnregistreurs(List<Enregistreur> enregistreurs) {
        this.enregistreurs = enregistreurs;
    }

    public List<Mesure> getMesures() {
        return mesures;
    }

    public void setMesures(List<Mesure> mesures) {
        this.mesures = mesures;
    }

}

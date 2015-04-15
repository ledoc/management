package fr.treeptik.shared.dto.capteur;

import java.util.List;

public class OuvrageDTO {

    private int id;
    private String name;
    private List<EnregistreurDTO> enregistreur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnregistreur(List<EnregistreurDTO> enregistreur) {
        this.enregistreur = enregistreur;
    }

    public List<EnregistreurDTO> getEnregistreur() {
        return enregistreur;
    }
}

package fr.treeptik.shared.dto.capteur;


import java.util.List;

public class SiteDTO {

    private int id;
    private String name;
    private List<OuvrageDTO> ouvrages;

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

    public List<OuvrageDTO> getOuvrages() {
        return ouvrages;
    }

    public void setOuvrages(List<OuvrageDTO> ouvrages) {
        this.ouvrages = ouvrages;
    }
}

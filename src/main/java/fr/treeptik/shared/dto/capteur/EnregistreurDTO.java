package fr.treeptik.shared.dto.capteur;


import java.util.List;

public class EnregistreurDTO {

    private int id;
    private String name;
    private List<CapteurDTO> capteurs;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCapteurs(List<CapteurDTO> capteurs) {
        this.capteurs = capteurs;
    }

    public List<CapteurDTO> getCapteurs() {
        return capteurs;
    }
}

package fr.treeptik.model.assembler;


import fr.treeptik.model.Ouvrage;
import fr.treeptik.shared.dto.capteur.OuvrageDTO;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

public class OuvrageAssembler {

    private EnregistreurAssembler enregistreurAssembler;

    public OuvrageAssembler(EnregistreurAssembler enregistreurAssembler)
    {
        this.enregistreurAssembler = enregistreurAssembler;
    }


    public List<OuvrageDTO> toDTOs(List<Ouvrage> beans) {
        List<OuvrageDTO> list = new ArrayList<OuvrageDTO>();
        for (Ouvrage bean : beans) {
            list.add(toDTO(bean));
        }
        return list;
    }

    private OuvrageDTO toDTO(Ouvrage bean) {
        OuvrageDTO dto = new OuvrageDTO();
        dto.setId(bean.getId());
        dto.setName(bean.getNom());
        if(Hibernate.isInitialized(bean.getEnregistreurs())){
            dto.setEnregistreur(enregistreurAssembler.toDTOs(bean.getEnregistreurs()));
        }
        return dto;
    }
}

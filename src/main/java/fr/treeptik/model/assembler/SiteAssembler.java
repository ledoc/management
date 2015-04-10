package fr.treeptik.model.assembler;


import fr.treeptik.model.Site;
import fr.treeptik.shared.mesure.dto.request.SiteDTO;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

public class SiteAssembler {

    private OuvrageAssembler ouvrageAssembler;

    public SiteAssembler(OuvrageAssembler ouvrageAssembler) {
        this.ouvrageAssembler = ouvrageAssembler;
    }

    public List<SiteDTO> toDTOs(List<Site> beans) {
        List<SiteDTO> list = new ArrayList<SiteDTO>();
        for (Site bean : beans) {
            list.add(toDTO(bean));
        }
        return list;
    }

    public SiteDTO toDTO(Site bean) {
        SiteDTO dto = new SiteDTO();
        dto.setId(bean.getId());
        dto.setName(bean.getNom());
        if(Hibernate.isInitialized(bean.getOuvrages())){
            dto.setOuvrages(ouvrageAssembler.toDTOs(bean.getOuvrages()));
        }
        return dto;
    }

}

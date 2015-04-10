package fr.treeptik.model.assembler;

import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.shared.mesure.dto.request.CapteurDTO;
import fr.treeptik.shared.mesure.dto.request.EnregistreurDTO;

import java.util.ArrayList;
import java.util.List;

public class CapteurAssembler {

    public List<CapteurDTO> toDTOs(List<Capteur> beans) {
        List<CapteurDTO> list = new ArrayList<CapteurDTO>();
        for (Capteur bean : beans) {
            list.add(toDTO(bean));
        }
        return list;
    }

    private CapteurDTO toDTO(Capteur bean) {
        CapteurDTO dto = new CapteurDTO();
        dto.setId(bean.getId());
        dto.setName(bean.getTypeCaptAlerteMesure().getDescription());
        return dto;
    }

}

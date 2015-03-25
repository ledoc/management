package fr.treeptik.model.assembler;


import fr.treeptik.model.Enregistreur;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EnregistreurAssembler {

    public static final String PARAMETER_VALID = "valid";
    public static final String PARAMETER_PERIOD = "period";
    public static final String PARAMETER_LOCALIZABLE_STATUS = "localizableStatus";
    public static final String PARAMETER_CLIENT_NAME = "clientName";
    public static final String PARAMETER_MID = "mid";
    public static final String PARAMETER_UNTIL = "until";
    public static final String PARAMETER_PID = "pid";
    public static final String PARAMETER_COMMENT = "comment";
    public static final String PARAMETER_TYPE = "type";
    public static final String PARAMETER_USER_NAME = "userName";

    public Enregistreur fromXmlrpcHashMap(HashMap<String, Object> xmlrpcHashMap) {
        Enregistreur enregistreur = new Enregistreur();
        enregistreur.setValid((boolean) xmlrpcHashMap.get(PARAMETER_VALID));
        enregistreur.setPeriod((int) xmlrpcHashMap.get(PARAMETER_PERIOD));
        enregistreur.setLocalizableStatus((int) xmlrpcHashMap.get(PARAMETER_LOCALIZABLE_STATUS));
        enregistreur.setClientName((String) xmlrpcHashMap.get(PARAMETER_CLIENT_NAME));
        enregistreur.setMid((String) xmlrpcHashMap.get(PARAMETER_MID));
        enregistreur.setUntil((int) xmlrpcHashMap.get(PARAMETER_UNTIL));
        enregistreur.setPid((String) xmlrpcHashMap.get(PARAMETER_PID));
        enregistreur.setComment((String) xmlrpcHashMap.get(PARAMETER_COMMENT));
        enregistreur.setType((String) xmlrpcHashMap.get(PARAMETER_TYPE));
        enregistreur.setUserName((String) xmlrpcHashMap.get(PARAMETER_USER_NAME));
       return enregistreur;
    }

}

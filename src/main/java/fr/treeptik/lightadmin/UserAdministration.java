package fr.treeptik.lightadmin;

import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.ScreenContextConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.ScreenContextConfigurationUnit;

import fr.treeptik.model.User;

public class UserAdministration extends AdministrationConfiguration<User> {

    public EntityMetadataConfigurationUnit configuration(
            EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.nameField("nom").build();
    }

    public ScreenContextConfigurationUnit screenContext(
            ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        return screenContextBuilder.screenName("Users Administration").build();
    }

    public FieldSetConfigurationUnit listView(
            final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder.field("nom").caption("Nom")
                .field("prenom").caption("Prénom")
                .field("telFixe").caption("Numéro de téléphone fixe")
                .field("telPortable").caption("Numéro de téléphone portable")
                .field("mail1").caption("Mail 1")
                .field("mail2").caption("Mail 2")
                .field("motDePasse").caption("Mot de passe").build();
    }
    
    public FieldSetConfigurationUnit formView(
            final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder.field("nom").caption("Nom")
                .field("prenom").caption("Prénom")
                .field("telFixe").caption("Numéro de téléphone fixe")
                .field("telPortable").caption("Numéro de téléphone portable")
                .field("mail1").caption("Mail 1")
                .field("mail2").caption("Mail 2")
                .field("motDePasse").caption("Mot de passe").build();
    }
    
}

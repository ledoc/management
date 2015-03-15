INSERT IGNORE INTO `Administrateur`  VALUES (1,'ID-1','administrateur','rvfontbonne@hotmail.com',NULL,'aaa','administrateurNom','adminPreNom','ADMIN',NULL,'0623456789');

INSERT IGNORE INTO `Administrateur`  VALUES (2, 'ID-2', 'pmauzet', 'philippe.mauzet@ah2d.fr', 'p.mauzet@yahoo.fr', 'solices', 'Mauzet', 'philippe', 'ADMIN', '0442513922', '0679468991'),
INSERT IGNORE INTO `Administrateur`  VALUES (3, 'ID-3', 'pleon', 'pleon@solices.fr', 'pleon2@solices.fr', 'solices', 'Leon', 'philippe', 'ADMIN', '0123456789', '0623456789'),
INSERT IGNORE INTO `Administrateur`  VALUES (4, 'ID-4', 'nserano', 'nserano@solices.fr', 'nserano2@solices.fr', 'solices', 'Serano', 'nicolas', 'ADMIN', '0123456789', '0623456789');

INSERT IGNORE INTO `Client` VALUES (1,'ID-1','client','h.fontbonne@treeptik.fr','client2@solices.fr','client','clientnom01','chef','clientprenom01','CLIENT','0187575529','0687575529');

INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (1, 'EAUDESURFACE', 'eau de surface');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (2, 'NAPPESOUTERRAINE', 'nappe souterraine');

INSERT IGNORE INTO TypeCaptAlerteMesure (id, nom, description) values (1, 'NIVEAUDEAU', 'niveau d''eau');
INSERT IGNORE INTO TypeCaptAlerteMesure (id, nom, description) values (2, 'NIVEAUMANUEL', 'niveau manuel');
INSERT IGNORE INTO TypeCaptAlerteMesure (id, nom, description) values (3, 'CONDUCTIVITE', 'conductivité');
INSERT IGNORE INTO TypeCaptAlerteMesure (id, nom, description) values (4, 'TEMPERATURE', 'température');

INSERT IGNORE INTO `Etablissement` VALUES (1,'etaCode1','48.86542/2.44385','','assoc',48.8654,2.44385,'solices@solices.fr','etaNom1','34523344','treeptik.fr','0123456789'),(2,'etaCode2','47.86542/2.14385','','assoc',47.8654,2.14385,'solices2@solices.fr','etaNom2','333333','treeptik.fr','0123456789');

INSERT IGNORE INTO `Site` VALUES (1,'siteCode1','50.59718/1.97753','','13',50.5972,1.97753,'siteNom1','ventoux','SITE',1),(2,'siteCode2','50.59718/1.97753','','13',50.5972,1.97753,'siteNom2','ventoux','SITE',1);

INSERT IGNORE INTO `Ouvrage` VALUES (1,'ouvrageCode1','','43.53157/5.25222','',1223,NULL,1234.43,NULL,43.5316,5.25222,120,110,'ouvrageNom1','0x00123t/123z',NULL,'\0',NULL,1,2);



INSERT IGNORE INTO `Enregistreur` VALUES (1,230,'',NULL,1,NULL,'',NULL,NULL,'\0','gps://ORANGE/+33781916177',NULL,95,'Silex sonde 10m','',NULL,NULL,0,'','','',NULL,'ANALOGIQUE',NULL,NULL,true,1);
/**
INSERT IGNORE INTO `Enregistreur` VALUES (2,230,'',NULL,1,NULL,'',NULL,NULL,'\0','gps://ORANGE/+33687575529',NULL,50,'fontbonne','',NULL,NULL,0,'','','',NULL,'ANALOGIQUE',NULL,NULL,true,1);
 */


INSERT IGNORE INTO `Capteur` VALUES (1,4,0,1,3);
/**
INSERT IGNORE INTO `Capteur` VALUES (2,4,0,2,1);
INSERT IGNORE INTO `Capteur` VALUES (3,4,0,2,3);
INSERT IGNORE INTO `Capteur` VALUES (4,20,0,2,4);




 * 
 */
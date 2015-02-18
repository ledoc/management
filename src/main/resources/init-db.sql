INSERT IGNORE INTO `Administrateur`  VALUES (1,'adminPrenom','administrateur','rv@rv.com',NULL,'aaa','administrateur01','adminNom','ADMIN',NULL,'0123456789');

INSERT IGNORE INTO `Client` VALUES (1,'ID-1','client','client@solices.fr','client2@solices.fr','client','clientnom01','chef','clientprenom01','CLIENT','0187575529','0687575529');

INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (1, 'EAUDESURFACE', 'eau de surface');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (2, 'NAPPESOUTERRAINE', 'nappe souterraine');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (3, 'PLUVIOMETRIE', 'pluviométrie');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (4, 'AIR', 'air');

INSERT IGNORE INTO `Etablissement` VALUES (1,'etaCode1','48.86542/2.44385','','assoc',48.8654,2.44385,'solices@solices.fr','etaNom1','34523344','treeptik.fr','0123456789');
INSERT IGNORE INTO `Etablissement` VALUES (2,'etaCode2','47.86542/2.14385','','assoc',47.8654,2.14385,'solices2@solices.fr','etaNom2','333333','treeptik.fr','0123456789');

INSERT IGNORE INTO `Site` VALUES (1,'siteCode1','50.59718/1.97753','','13',50.5972,1.97753,'siteNom1','ventoux','SITE',1),(2,'siteCode2','50.59718/1.97753','','13',50.5972,1.97753,'siteNom2','ventoux','SITE',1);

INSERT IGNORE INTO `Ouvrage` VALUES (1,'ouvrageCode1','','43.53157/5.25222','',1223,NULL,1234.43,NULL,43.5316,5.25222,120,110,'ouvrageNom1','0x00123t/123z',NULL,'\0',NULL,1,2);

INSERT IGNORE INTO `Enregistreur` VALUES (1,NULL,'4.2',NULL,NULL,NULL,'',NULL,NULL,'\0','gps://ORANGE/+33781916177','',95,'solex',NULL,NULL,'1234567','1234','satellite',NULL,NULL,NULL,NULL,FALSE,1);

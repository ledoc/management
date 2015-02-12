INSERT IGNORE INTO Administrateur (id, nom, prenom, identifiant, login, motDePasse, role, telPortable, mail1) values (1, 'administrateur01','fontbonne', 'herve', 'administrateur', 'aaa', 'ADMIN', '0123456789', 'rv@rv.com');

INSERT IGNORE INTO Client (id, identifiant, login, motDePasse, role) values (1, 'client01', 'client', 'client', 'CLIENT');

INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (1, 'EAUDESURFACE', 'eau de surface');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (2, 'NAPPESOUTERRAINE', 'nappe souterraine');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (3, 'PLUVIOMETRIE', 'pluviométrie');
INSERT IGNORE INTO TypeOuvrage (id, nom, description) values (4, 'AIR', 'air');

INSERT IGNORE INTO `Etablissement` VALUES (1,'eta1','43.52635/5.44666','Assoc','43.52635','5.44666','rv@rv.com','etacode1','12345','treeptik.fr','0687575529');
INSERT IGNORE INTO `Etablissement` VALUES (2,'eta2','43.53157/5.25222','SARL','43.53157','5.25222','rv@rv.com','etacode2','34523344','','0687575529');

INSERT IGNORE INTO `Site` VALUES (1,'codesite1','48.16608/-4.08691','13',48.1661,-4.08691,'site1','ventoux','SITE',2),(2,'codesite2','46.58906/0.43945','14',46.5891,0.43945,'site2','ventoux','SITE',1);
ALTER TABLE tst.evenement ADD id_createur INTEGER;
ALTER TABLE tst.evenement ADD FOREIGN KEY(id_createur) references tst.utilisateurs(idutilisateur);

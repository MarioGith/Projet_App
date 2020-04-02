ALTER TABLE tst.evenement
    ADD image MEDIUMBLOB;
ALTER TABLE tst.evenement
    ADD horaire varchar(20) NOT NULL;
ALTER TABLE tst.lien DROP id;
ALTER TABLE tst.lien ADD PRIMARY KEY(idevent,idutilisateur);
ALTER TABLE tst.utilisateurs DROP login;
ALTER TABLE tst.utilisateurs ADD email VARCHAR(30);

ALTER TABLE tst.lien DROP id;
ALTER TABLE tst.lien ADD PRIMARY KEY(idevent,idutilisateur);
ALTER TABLE tst.utilisateurs DROP login;
ALTER TABLE tst.utilisateurs ADD email VARCHAR(30) NOT NULL;


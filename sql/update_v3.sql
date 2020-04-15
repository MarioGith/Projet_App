ALTER TABLE tst.evenement
    ADD date varchar(20) NOT NULL;
ALTER TABLE tst.evenement
    DROP image;
ALTER TABLE tst.evenement
    ADD image_pre mediumblob;
ALTER TABLE tst.evenement
    ADD menu mediumblob;
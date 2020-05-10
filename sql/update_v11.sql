ALTER TABLE tst.utilisateurs MODIFY password char(255);
ALTER TABLE tst.utilisateurs ADD regtime Timestamp;
ALTER TABLE tst.utilisateurs ADD newpassdemandtime Timestamp;
ALTER TABLE tst.utilisateurs MODIFY token varchar(50) UNIQUE;

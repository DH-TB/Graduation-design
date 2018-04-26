CREATE TABLE music(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    musicName VARCHAR(255),
    album VARCHAR(255),
    type VARCHAR(10),
    signerId INT(10),
    FOREIGN KEY (signerId) REFERENCES signer(id)
) engine=InnoDB DEFAULT CHARSET = utf8;

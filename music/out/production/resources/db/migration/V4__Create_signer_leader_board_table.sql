CREATE TABLE signerLeaderBoard(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(255),
    hot INT(100),
    signerId INT(10),
    FOREIGN KEY (signerId) REFERENCES signer(id)
) engine=InnoDB DEFAULT CHARSET = utf8;


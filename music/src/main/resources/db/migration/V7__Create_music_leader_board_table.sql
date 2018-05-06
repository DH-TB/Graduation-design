CREATE TABLE musicLeaderBoard(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(255),
    type VARCHAR(255),
    hot INT(10),
    musicId INT(10)
) engine=InnoDB DEFAULT CHARSET = utf8;


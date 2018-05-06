CREATE TABLE singerLeaderBoard(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(255),
    hot INT(100),
    singerId INT(10)
) engine=InnoDB DEFAULT CHARSET = utf8;


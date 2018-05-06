CREATE TABLE music(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    music VARCHAR(255),
    album VARCHAR(255),
    type VARCHAR(10),
    singerId INT(10),
    collected INT(10),
    image VARCHAR(255)
) engine=InnoDB DEFAULT CHARSET = utf8;

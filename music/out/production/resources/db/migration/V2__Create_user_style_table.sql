CREATE TABLE userStyle(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INT(10),
    jazz INT(10),
    rock INT(10),
    hiphop INT(10),
    light INT(10),
    blues INT(10),
    classical INT(10),
    pop INT(10),
    heavyMetal INT(10),
    rap INT(10),
    folk INT(10),
    FOREIGN KEY (userId) REFERENCES user(id)
) engine=InnoDB DEFAULT CHARSET = utf8;
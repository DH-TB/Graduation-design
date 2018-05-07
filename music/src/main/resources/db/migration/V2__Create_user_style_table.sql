CREATE TABLE userStyle(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId INT(10),
    jazz DOUBLE,
    rock DOUBLE,
    hiphop DOUBLE,
    light DOUBLE,
    blues DOUBLE,
    classical DOUBLE,
    pop DOUBLE,
    heavyMetal DOUBLE,
    rap DOUBLE,
    folk DOUBLE
) engine=InnoDB DEFAULT CHARSET = utf8;
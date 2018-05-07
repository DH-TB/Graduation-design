CREATE TABLE musicStyle(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jazz DOUBLE,
    rock DOUBLE,
    hiphop DOUBLE,
    light DOUBLE,
    blues DOUBLE,
    classical DOUBLE,
    pop DOUBLE,
    heavyMetal DOUBLE,
    rap DOUBLE,
    folk DOUBLE,
    musicId INT(10)
) engine=InnoDB DEFAULT CHARSET = utf8;

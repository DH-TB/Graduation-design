CREATE TABLE comment(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(1000),
    count INT(10),
    musicId INT(10),
    userId INT(10),
    FOREIGN KEY (musicId) REFERENCES music(id),
    FOREIGN KEY (userId) REFERENCES user(id)
) engine=InnoDB DEFAULT CHARSET = utf8;


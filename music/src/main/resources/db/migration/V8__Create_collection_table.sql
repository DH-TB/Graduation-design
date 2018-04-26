CREATE TABLE collection(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    playCount INT(10),
    love INT(10),
    musicId int(10),
    userId int(10),
    FOREIGN KEY (musicId) REFERENCES music(id),
    FOREIGN KEY (userId) REFERENCES user(id)
) engine=InnoDB DEFAULT CHARSET = utf8;

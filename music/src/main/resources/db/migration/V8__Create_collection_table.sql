CREATE TABLE collection(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    count INT(10),
    love INT(10),
    musicId int(10),
    userId int(10)
) engine=InnoDB DEFAULT CHARSET = utf8;


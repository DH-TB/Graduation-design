CREATE TABLE comment(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(1000),
    count INT(10),
    musicId INT(10),
    userId INT(10),
    isLike INT(10),
    nickName VARCHAR(1000),
    image VARCHAR(255),
    createTime TIMESTAMP default current_timestamp
) engine=InnoDB DEFAULT CHARSET = utf8;
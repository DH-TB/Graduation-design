CREATE TABLE user(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    image VARCHAR(255)
) engine=InnoDB DEFAULT CHARSET = utf8;


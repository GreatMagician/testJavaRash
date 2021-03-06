CREATE TABLE user
(id INT(8) AUTO_INCREMENT UNIQUE,
name VARCHAR(25),
age INT,
isAdmin BIT,
createdDate TIMESTAMP,
PRIMARY KEY (id))
ENGINE INNODB CHARACTER SET UTF8;

INSERT INTO user (name, age, isAdmin, createdDate)
VALUES
('Пётр', 23, 0, '2016-08-13 16:28:07'),
('Александр', 32, 1, '2016-08-13 16:28:07'),
('Василий', 21, 0, '2016-08-13 16:28:07'),
('Анжелика', 43, 1, '2016-08-13 16:28:07'),
('Владимир', 27, 0, '2016-08-13 16:28:07'),
('Евгений', 38, 1, '2016-08-13 16:28:07'),
('Мария', 35, 0, '2016-08-13 16:28:07'),
('Света', 20, 0, '2016-08-13 16:28:07'),
('Артём', 17, 0, '2016-08-13 16:28:07'),
('Игорь', 44, 0, '2016-08-13 16:28:07');
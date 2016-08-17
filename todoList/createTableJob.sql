CREATE TABLE job
(id INT AUTO_INCREMENT UNIQUE,
name VARCHAR(255),
made BIT,
time VARCHAR(30),
PRIMARY KEY (id))
ENGINE INNODB CHARACTER SET UTF8;

INSERT INTO job (name, made, time)
VALUES
('Установить MySql', 1, '10.08.16'),
('Установить Maven', 1, '10.08.16'),
('Разобраться с Maven', 1, 'до 11.08.2016'),
('Разобраться с hibernate', 1, 'до 12.08.16 '),
('Разобраться с jsp', 1, '14.08.16'),
('Разобраться в GitHub', 1, ' до 17.08.16');
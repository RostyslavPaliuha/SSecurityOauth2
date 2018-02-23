CREATE TABLE User (user_id  LONG AUTO_INCREMENT , name VARCHAR(255) NULL, password VARCHAR(255) NULL, age INT(11), salary DOUBLE, PRIMARY KEY(user_id));
CREATE TABLE Role (role_id int(11), name VARCHAR(255), PRIMARY KEY (role_id));
CREATE TABLE User_Role (user_id int(11) NOT NULL,role_id int(11) NOT NULL, PRIMARY KEY (user_id,role_id), FOREIGN KEY (user_id) REFERENCES USER (user_id), FOREIGN KEY (role_id) REFERENCES ROLE (role_id));

INSERT INTO USER (name, password, age, salary) VALUES ('Rostyslav', '1111', 25, 400);
INSERT INTO USER (name, password, age, salary) VALUES ('Taras', '1111', 25, 400);
INSERT INTO USER (name, password, age, salary) VALUES ('Mukola', '1111', 25, 400);
INSERT INTO USER (name, password, age, salary) VALUES ('Vasul', '1111', 25, 400);
INSERT INTO ROLE VALUES (1,'admin'),(2,'user');
INSERT INTO user_role VALUES (1,1);
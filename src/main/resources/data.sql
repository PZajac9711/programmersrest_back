DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS post cascade;
DROP TABLE IF EXISTS comment cascade;
DROP TABLE IF EXISTS subcomment cascade;
DROP TABLE IF EXISTS tag cascade;
DROP TABLE IF EXISTS refresh_token;
CREATE TABLE user(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    join_date DATE NOT NULL,
    active boolean default true,
    not_banned boolean default true,
    role VARCHAR(30) NOT NULL
);
CREATE TABLE post(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
    title varchar(100) NOT NULL UNIQUE,
    short_description VARCHAR(300) NOT NULL,
    full_description TEXT NOT NULL,
    create_date DATETIME NOT NULL,
    author VARCHAR(40) NOT NULL,
    last_modified DATETIME,
    imagine_path VARCHAR(1024) NOT NULL,
    active boolean default false
);
CREATE TABLE comment(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
    post_id INT(6),
    author VARCHAR(40),
    description TEXT,
    create_date DATETIME,
    score INT(6) default 0,
    FOREIGN KEY (post_id) REFERENCES post(id)
        ON DELETE CASCADE
);
CREATE TABLE subcomment(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
    comment_id INT(6),
    author VARCHAR(40) NOT NULL,
    create_date DATETIME NOT NULL,
    description TEXT,
    FOREIGN KEY (comment_id) REFERENCES comment(id)
        ON DELETE CASCADE
);
CREATE TABLE tag(
    id INT(6) AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(40) NOT NULL,
    post_id INT(6) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(id)
);
CREATE TABLE refresh_token(
    id INT(6) AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(40) NOT NULL ,
    token VARCHAR(555) NOT NULL
);
INSERT INTO user(username, password, email, join_date, active, role) VALUES ( 'admin','$2a$10$z7EinrtlpHpWZ1OlPgLnd.NRFFV81q6.Zm49UkVbFRAhGuyykyYTe','admin@admin.admin',CURRENT_DATE,TRUE,'ADMIN' );
INSERT INTO user(username, password, email, join_date, active, role) VALUES ( 'user','$2a$10$z7EinrtlpHpWZ1OlPgLnd.NRFFV81q6.Zm49UkVbFRAhGuyykyYTe','admin@admin.admin',CURRENT_DATE,TRUE,'USER' );
INSERT INTO post(title, short_description, full_description, create_date, author, imagine_path,active)
VALUES ( 'title','t1','t2',CURRENT_DATE,'admin','path',true);
INSERT INTO post(title, short_description, full_description, create_date, author, imagine_path)
VALUES ( 'title2','t1','t2',CURRENT_DATE,'admin','path');
INSERT INTO post(title, short_description, full_description, create_date, author, imagine_path,active)
VALUES ( 'title3','t1','t2',CURRENT_DATE,'admin','path',true);
INSERT INTO post(title, short_description, full_description, create_date, author, imagine_path)
VALUES ( 'title4','t1','t2',CURRENT_DATE,'admin','path');
INSERT INTO post(title, short_description, full_description, create_date, author, imagine_path)
VALUES ( 'title5','t1','t2',CURRENT_DATE,'admin','path');
INSERT INTO comment(post_id, author, description, create_date, score) VALUES ( 1,'admin','desc',CURRENT_DATE, 0 );
INSERT INTO subcomment(comment_id, author, create_date, description) VALUES ( 1,'admin',CURRENT_DATE,'asd' );

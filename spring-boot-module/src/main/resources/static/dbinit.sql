DROP TABLE IF EXISTS t_user;
DROP TABLE IF EXISTS t_role;
DROP TABLE IF EXISTS t_item;
CREATE TABLE IF NOT EXISTS t_item( F_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, F_NAME VARCHAR(80) NOT NULL, F_STATUS VARCHAR(20) NOT NULL, F_DELETED BOOLEAN NOT NULL) ENGINE InnoDB
CREATE TABLE IF NOT EXISTS t_role( F_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, F_NAME VARCHAR(30) NOT NULL, F_DELETED BOOLEAN NOT NULL) ENGINE InnoDB
CREATE TABLE IF NOT EXISTS t_user( F_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, F_USERNAME VARCHAR(70) NOT NULL, F_PASSWORD VARCHAR(80) NOT NULL, F_ROLE VARCHAR(30) NOT NULL, F_DELETED BOOLEAN NOT NULL) ENGINE InnoDB
INSERT INTO t_item VALUES (F_ID, "itemName1", "READY", false)
INSERT INTO t_item VALUES (F_ID, "itemName1", "STEADY", false)
INSERT INTO t_role VALUES (F_ID, "Administrator", false)
INSERT INTO t_role VALUES (F_ID, "Customer", false)
INSERT INTO t_user VALUES (F_ID, "admin", "admin", "Administrator", false)
INSERT INTO t_user VALUES (F_ID, "user", "user", "Customer", false)
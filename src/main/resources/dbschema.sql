DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS log;

CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL,
    username VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS authorities(
      user_id bigint NOT NULL,
      authority VARCHAR(255) NOT NULL,
      CONSTRAINT fk_authorities_users FOREIGN KEY(user_id) REFERENCES users (id));
;

CREATE TABLE IF NOT EXISTS log (
	  id bigint NOT NULL,
    logstring VARCHAR(1000) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS test (
	  id bigint NOT NULL,
    testcolumn VARCHAR(1000) NULL,
    PRIMARY KEY (id)
);




CREATE TABLE address
(
  id          SERIAL NOT NULL
    CONSTRAINT address_id_pk
    PRIMARY KEY,
  value       VARCHAR(100),
  postal_code VARCHAR(6),
  region      VARCHAR(20),
  city        VARCHAR(30),
  settlement  VARCHAR(30),
  street      VARCHAR(30),
  house       VARCHAR(5)
);

CREATE UNIQUE INDEX address_id_uindex
  ON address (id);

CREATE TABLE request
(
  id            SERIAL NOT NULL
    CONSTRAINT suggest_request_pkey
    PRIMARY KEY,
  query         VARCHAR(100),
  time_of_query TIMESTAMP,
  count_use     INTEGER
);

CREATE UNIQUE INDEX suggest_request_id_uindex
  ON request (id);

CREATE TABLE address_and_request
(
  id         SERIAL  NOT NULL
    CONSTRAINT address_and_request_pkey
    PRIMARY KEY,
  id_address INTEGER NOT NULL
    CONSTRAINT address_and_request_address_id_fk
    REFERENCES address
    ON UPDATE CASCADE ON DELETE CASCADE,
  id_request INTEGER NOT NULL
    CONSTRAINT address_and_request_request_id_fk
    REFERENCES request
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE UNIQUE INDEX address_and_request_id_uindex
  ON address_and_request (id);
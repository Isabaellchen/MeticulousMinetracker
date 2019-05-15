CREATE TABLE block_destroyed (

  x NUMERIC NOT NULL,
  y NUMERIC NOT NULL,
  z NUMERIC NOT NULL,
  material NUMERIC NOT NULL,
  time TIMESTAMP NOT NULL,
  block_id BIGINT UNIQUE AUTO_INCREMENT,
  is_restored BOOL NOT NULL,

  PRIMARY KEY (x,y,z),
  INDEX (block_id)
);
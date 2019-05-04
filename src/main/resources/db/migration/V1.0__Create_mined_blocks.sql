CREATE TABLE mined_blocks (
  x NUMERIC NOT NULL,
  y NUMERIC NOT NULL,
  z NUMERIC NOT NULL,
  material NUMERIC NOT NULL,
  time TIMESTAMP NOT NULL,
  block_id MEDIUMINT AUTO_INCREMENT,
  PRIMARY KEY (x,y,z),
  INDEX (block_id)
)
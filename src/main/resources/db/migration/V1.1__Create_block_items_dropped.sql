CREATE TABLE block_items_dropped (

  block_id BIGINT NOT NULL,
  material NUMERIC NOT NULL,
  amount NUMERIC NOT NULL,

  PRIMARY KEY (block_id, material),
  FOREIGN KEY (block_id) REFERENCES block_destroyed(block_id)
);
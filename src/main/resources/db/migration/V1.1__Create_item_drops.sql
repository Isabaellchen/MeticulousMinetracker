CREATE TABLE item_drops (
  mined_block_id NUMERIC NOT NULL,
  material NUMERIC NOT NULL,
  amount NUMERIC NOT NULL,
  PRIMARY KEY (mined_block_id, material)
)
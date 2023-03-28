CREATE TABLE product (
  id INT PRIMARY KEY,
  sequence INT NOT NULL CHECK (sequence > 0)
);

CREATE TABLE size (
  id INT PRIMARY KEY,
  product_id INT NOT NULL,
  backsoon BOOLEAN NOT NULL CHECK (backsoon IN (false, true)),
  special BOOLEAN NOT NULL CHECK (special IN (false, true)),
  FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE stock (
  size_id INT PRIMARY KEY,
  quantity INT NOT NULL CHECK (quantity >= 0),
  FOREIGN KEY (size_id) REFERENCES size(id)
);

INSERT INTO product (id, sequence)
SELECT id, sequence
FROM CSVREAD('classpath:samples/${csv.folder}/product.csv');

INSERT INTO size (id, product_id, backsoon, special)
SELECT s.id, s.product_id, s.backsoon, s.special
FROM CSVREAD('classpath:samples/${csv.folder}/size.csv') s
INNER JOIN product p ON s.product_id = p.id;

INSERT INTO stock (size_id, quantity)
SELECT size_id, quantity
FROM CSVREAD('classpath:samples/${csv.folder}/stock.csv') t
INNER JOIN size s ON t.size_id = s.id;
CREATE TABLE product (
  id INT PRIMARY KEY,
  sequence INT NOT NULL
);

CREATE TABLE size (
  id INT PRIMARY KEY,
  product_id INT NOT NULL,
  backsoon BOOLEAN NOT NULL,
  special BOOLEAN NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE stock (
  size_id INT PRIMARY KEY,
  quantity INT NOT NULL,
  FOREIGN KEY (size_id) REFERENCES size(id)
);

INSERT INTO product (id, sequence)
SELECT id, sequence
FROM CSVREAD('classpath:product.csv');

INSERT INTO size (id, product_id, backsoon, special)
SELECT s.id, s.product_id, s.backsoon, s.special
FROM CSVREAD('classpath:size.csv') s
INNER JOIN product p ON s.product_id = p.id;

INSERT INTO stock (size_id, quantity)
SELECT size_id, quantity
FROM CSVREAD('classpath:stock.csv') t
INNER JOIN size s ON t.size_id = s.id;
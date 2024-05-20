CREATE TABLE cliente (
  customer_code VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   phone VARCHAR(255),
   customer_type VARCHAR(255),
   customer_registered VARCHAR(255),
   CONSTRAINT pk_cliente PRIMARY KEY (customer_code)
);
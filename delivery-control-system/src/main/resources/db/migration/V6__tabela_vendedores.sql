CREATE TABLE vendedores (
  seller_id UUID NOT NULL,
   sellers_name VARCHAR(255) NOT NULL,
   sellersrca VARCHAR(255) NOT NULL,
   CONSTRAINT pk_vendedores PRIMARY KEY (seller_id)

);
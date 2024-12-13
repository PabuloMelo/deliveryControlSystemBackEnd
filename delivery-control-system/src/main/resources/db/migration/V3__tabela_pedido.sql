CREATE TABLE pedido (
  order_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   order_number VARCHAR(255) NOT NULL,
   customer_code VARCHAR(255),
   loadid BIGINT,
   order_type VARCHAR(255),
   status VARCHAR(255),
   purchase_date date NOT NULL,
   invoicing_date date NOT NULL,
   CONSTRAINT pk_pedido PRIMARY KEY (order_id)
);

ALTER TABLE pedido ADD CONSTRAINT uc_pedido_order_number UNIQUE (order_number);

ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_ON_CUSTOMERCODE FOREIGN KEY (customer_code) REFERENCES cliente (customer_code);

ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_ON_LOADID FOREIGN KEY (loadID) REFERENCES carregamento (loadID);
CREATE TABLE pedido (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   order_code BIGINT NOT NULL,
   customer_id BIGINT,
   customer_code BIGINT NOT NULL,
   load_id BIGINT,
   load_number BIGINT NOT NULL,
   customer_name VARCHAR(255) NOT NULL,
   order_type VARCHAR(255),
   status VARCHAR(255),
   purchase_date date NOT NULL,
   invoicing_date date,
   seller_id BIGINT,
   sellers_rca BIGINT NOT NULL,
   seller_name VARCHAR(255) NOT NULL,
   days_until_delivery INT,
   order_future_del_state VARCHAR(255),
   CONSTRAINT pk_pedido PRIMARY KEY (id)
);

ALTER TABLE pedido ADD CONSTRAINT uc_pedido_order_code UNIQUE (order_code);

ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES cliente (customer_id);

ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_ON_LOAD FOREIGN KEY (load_id) REFERENCES carregamento (load_id);

ALTER TABLE pedido ADD CONSTRAINT FK_PEDIDO_ON_SELLERID FOREIGN KEY (seller_id) REFERENCES vendedores (seller_id);
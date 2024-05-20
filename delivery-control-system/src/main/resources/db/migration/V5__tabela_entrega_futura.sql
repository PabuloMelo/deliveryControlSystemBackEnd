CREATE TABLE "entrega futura" (
  id UUID NOT NULL,
   customer_id VARCHAR(255),
   order_id BIGINT,
   order_state VARCHAR(255),
   days_until_delivery INT NOT NULL,
   CONSTRAINT pk_entrega_futura PRIMARY KEY (id)
);

ALTER TABLE "entrega futura" ADD CONSTRAINT FK_ENTREGA_FUTURA_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES cliente (customer_code);

ALTER TABLE "entrega futura" ADD CONSTRAINT FK_ENTREGA_FUTURA_ON_ORDER FOREIGN KEY (order_id) REFERENCES pedido (order_id);
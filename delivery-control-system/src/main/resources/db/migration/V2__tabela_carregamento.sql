CREATE TABLE carregamento (
  loadid BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   load_number VARCHAR(255),
   driver SMALLINT,
   departure_date date NOT NULL,
   CONSTRAINT pk_carregamento PRIMARY KEY (loadID)
);
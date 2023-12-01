CREATE TABLE operation_types (
  Operation_Type_ID INTEGER NOT NULL,
  Description VARCHAR NOT NULL,
  CONSTRAINT pk_operationtypes PRIMARY KEY (Operation_Type_ID)
);

INSERT INTO operation_types(Operation_Type_ID, Description) VALUES(1, 'COMPRA A VISTA');
INSERT INTO operation_types(Operation_Type_ID, Description) VALUES(2, 'COMPRA PARCELADA');
INSERT INTO operation_types(Operation_Type_ID, Description) VALUES(3, 'SAQUE');
INSERT INTO operation_types(Operation_Type_ID, Description) VALUES(4, 'PAGAMENTO');
CREATE TABLE accounts (
  Account_ID UUID NOT NULL,
  Document_Number VARCHAR UNIQUE NOT NULL,
  PRIMARY KEY (Account_ID)
);

CREATE TABLE transactions (
  Transaction_ID UUID NOT NULL,
  Amount VARCHAR NOT NULL,
  Event_Date TIMESTAMP NOT NULL DEFAULT NOW(),
  Account_ID uuid NOT NULL,
  Operation_Type_ID INTEGER NOT NULL,
  CONSTRAINT pk_transactions PRIMARY KEY (Transaction_ID),
  CONSTRAINT fk_account FOREIGN KEY(Account_ID) REFERENCES accounts(Account_ID)
);
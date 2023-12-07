CREATE TABLE account_credit_limit (
  Id UUID NOT NULL,
  Account_ID UUID UNIQUE NOT NULL,
  AvailableCreditLimit VARCHAR(255) NOT NULL,
  TransactionAggregate VARCHAR(255) DEFAULT '0' NOT NULL,
  CONSTRAINT fk_account FOREIGN KEY(Account_ID) REFERENCES accounts(Account_ID),
  PRIMARY KEY (Id)
);